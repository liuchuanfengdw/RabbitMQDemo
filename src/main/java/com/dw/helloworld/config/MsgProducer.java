package com.dw.helloworld.config;

import com.alibaba.fastjson.JSONObject;
import com.dw.helloworld.entity.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @Description: 消息生产者
 * @Author: DING WEI
 * @Date: 2019-03-30 16:53
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 此处的rabbirTemplate设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE类型
     * 顾不能自动注入spring容器
     */
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(UserDto userDto){
        CorrelationData correlationDataId = new CorrelationData(UUID.randomUUID().toString().replaceAll("-",""));
        // 把消息放进队列QUEUE_A
        ObjectMapper mapper = new ObjectMapper();
        try {
            String content = mapper.writeValueAsString(userDto);
            try {
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A,RabbitMQConfig.ROUTINGKEY_A,content.getBytes("utf-8"),correlationDataId);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgByQueueB(UserDto userDto){
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_B, JSONObject.toJSONString(userDto));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("回调id"+correlationData);
        if(ack){
            logger.info("消费成功");
        }else{
            logger.info("消费失败"+cause);
        }
    }
}
