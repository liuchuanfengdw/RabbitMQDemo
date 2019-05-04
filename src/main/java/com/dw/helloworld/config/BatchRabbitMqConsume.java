package com.dw.helloworld.config;

import com.alibaba.fastjson.JSON;
import com.dw.helloworld.entity.dobean.UserDo;
import com.dw.helloworld.entity.dto.LoginDto;
import com.dw.helloworld.service.UserService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description: 批量消费rabbitmq并手动ack
 * @Author: DING WEI
 * @Date: 2019-04-03 21:12
 */
//@Component
@Lazy(value = false)
public class BatchRabbitMqConsume {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private UserService userService;

    private static final String QUEUE_NAME = RabbitMQConfig.QUEUE_B;

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    ExecutorService executor = new ThreadPoolExecutor(1,1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);

    @PostConstruct
    public void init(){
        executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
//                execute(3);
                return null;
            }
        });
    }

    private void execute(int batchSize){
        while (true) {
            rabbitTemplate.execute(new ChannelCallback<String>() {
                @Override
                public String doInRabbit(Channel channel) throws Exception {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        final AMQP.Queue.DeclareOk ok = channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                        int messageCount = ok.getMessageCount();
                        logger.debug("batchSaveUser {}, msg count {}", sdf.format(new Date()), messageCount);
                        if (messageCount == 0) {
                            return null;
                        }
                        List<UserDo> list = new ArrayList<>();
                        channel.basicQos(batchSize);
                        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
                        logger.debug("channel id {}", Integer.toHexString(System.identityHashCode(channel)));
                        final String inConsumerTag = "batchSaveUser {}" + sdf.format(new Date());
                        channel.basicConsume(QUEUE_NAME, false, inConsumerTag, queueingConsumer);
                        long messageId = -1;
                        int dealedCount = 0;
                        int i = batchSize;
                        while (i-- > 0) {
                            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery(batchSize);
                            if (delivery == null) {
                                break;
                            }
                            String msg = new String(delivery.getBody());
                            JSONObject jsonObject = JSONObject.parseObject(msg);
                            LoginDto loginDto = JSON.toJavaObject(jsonObject, LoginDto.class);
                            list.add(dto2Do(loginDto));
                            messageId = delivery.getEnvelope().getDeliveryTag();
                            logger.info(" userId {}, delivery id {}", loginDto.getUsername(), messageId);
                            dealedCount++;
                            if (dealedCount % 5 == 0) {
                                channel.basicAck(messageId, true);
                                logger.debug("batch ack message id =>{}", messageId);
                                messageId = -1;
                            }
                        }
                        if (messageId > 0) {
                            channel.basicAck(messageId, true);
                            logger.debug("last to ack message id =>{}", messageId);
                        }

                        // 入库
                        userService.saveBatch(list);


                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("{}",e.getCause());
                    }finally {
                        logger.info("batchSaveUser done {}", sdf.format(new Date()));
                    }
                    channel.abort();
                    return null;
                }
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }

    }

    private UserDo dto2Do(LoginDto loginDto){
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(loginDto,userDo);
        return userDo;
    }
}
