package com.dw.helloworld.service;

import com.dw.helloworld.baseBean.ResultBean;
import com.dw.helloworld.entity.dobean.UserDo;
import com.dw.helloworld.entity.dto.LoginDto;
import com.dw.helloworld.entity.vo.LoginVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: DING WEI
 * @Data: 2019-03-30 11:18
 */
public interface UserService {

    UserDo saveUser(LoginDto loginDto);

    Integer saveBatch(List<UserDo> list);

    List<Map<String,Object>> findAllUser();

    LoginVo findByUserId(String userId);

    /**
     * 根据用户名查询
     * @Params: username 用户名
     * @Return: 用户
     * @Author: DING WEI
     * @Date: 2019-04-25 20:45
     * @Version: 1.0
     */
    LoginVo findByUserName(String username);

    /**
     * 登陆
     * @Param: loginDto
     * @Return: LoginVo
     * @Author: DING WEI
     * @Date: 2019-04-25 20:49
     * @Version: 1.0
     */
    ResultBean registOrLogin(LoginDto loginDto) throws Exception ;

    /**
     * 查询我的好友列表
     * @param: userId
     * @author: DING WEI
     * @date: 2019-05-03 09:08
     * @version: 1.0
     */
    ResultBean myFriends(String userId);
}
