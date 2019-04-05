package com.dw.helloworld.rest;

import com.dw.helloworld.baseBean.ResultBean;
import com.dw.helloworld.entity.vo.UserVo;
import com.dw.helloworld.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 控制类
 * @Author: DING WEI
 * @Date: 2019-03-24 16:27
 */
@RestController
@Api(value = "用户操作接口",tags = {"用户"})
public class HelloWorldController {

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    @ApiOperation(value = "hello")
    public String hello(){
        return "I'm From HellWorldController!";
    }

    @GetMapping("/find_all_user")
    @ApiOperation(value = "查询所有用户")
    public List<Map<String,Object>> findAllUser(){
        return userService.findAllUser();
    }

    @GetMapping(value = "/get_user_by_id")
    public ResultBean findByUserId(@RequestParam("userId") Long userId){
        return new ResultBean().success("查询成功",userService.findByUserId(userId));
    }

}
