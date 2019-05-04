package com.dw.helloworld.mvcpage;

import com.dw.helloworld.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author: DING WEI
 * @Date: 2019-04-28 21:58
 */
@Controller
@RequestMapping("/user")
public class TestMvcForJsp {

    @Resource
    private UserService userService;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView index() {
        // 设置对应JSP的模板文件
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    /**
     * 跳转聊天页面
     */
    @RequestMapping("/toChat")
    public ModelAndView toChat(String userId){
        ModelAndView modelAndView = new ModelAndView("/chat");
        modelAndView.addObject("userId",userId);
        return modelAndView;
    }
}
