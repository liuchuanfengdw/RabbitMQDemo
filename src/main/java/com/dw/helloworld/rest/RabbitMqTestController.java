package com.dw.helloworld.rest;

import com.dw.helloworld.config.MsgProducer;
import com.dw.helloworld.entity.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 测试rabbitmq
 * @Author: DING WEI
 * @Date: 2019-03-30 17:25
 */
@RestController
@Api(value = "mq消息控制器",tags = {"mq消息"})
public class RabbitMqTestController {

    @Resource
    private MsgProducer msgProducer;

    @PostMapping("send_msg_mq")
    @ApiOperation("发送消息")
    public void sendMsg(@RequestBody LoginDto loginDto){
        msgProducer.sendMsg(loginDto);
    }

    @PostMapping("send_user_queueb")
    @ApiOperation("通过mq批量保存用户")
    public void sendMsgByQueueB(@RequestBody LoginDto loginDto){
        msgProducer.sendMsgByQueueB(loginDto);
    }
}
