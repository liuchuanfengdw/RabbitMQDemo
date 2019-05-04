package com.dw.helloworld.rest;

import com.dw.helloworld.baseBean.ResultBean;
import com.dw.helloworld.entity.dto.LoginDto;
import com.dw.helloworld.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 登陆
 * @Author: DING WEI
 * @Date: 2019-04-25 20:32
 */
@RestController
@RequestMapping("/user")
@Api(value = "登陆等操作控制器",tags = {"登陆等操作控制器"})
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/registOrLogin")
    @ApiOperation("注册或者登陆")
    public ResultBean registOrLogin(@RequestBody LoginDto loginDto) throws Exception{
        return userService.registOrLogin(loginDto);
    }

    @GetMapping("/myFriends")
    public ResultBean contactList(String userId){
        return null;
    }
}
