package com.dw.helloworld.config;

import com.dw.helloworld.entity.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 消息消费者
 * @Author: DING WEI
 * @Date: 2019-03-30 17:21
 */
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_A)
public class MsgReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(LoginDto loginDto){
        logger.info("消费队列A中的消息:"+ loginDto.toString());
    }


}
