package com.dw.helloworld.dao;

import com.dw.helloworld.entity.dobean.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户
 * @Author: DING WEI
 * @Data: 2019-03-30 11:00
 */
@Mapper
public interface UserDao {

    Long save(UserDo userDo);

    Integer saveBatch(@Param("list") List<UserDo> list);

    List<Map<String,Object>> findAll();

    UserDo findById(String id);

    /**
     * 根据用户名查询
     * @Params: username 用户名
     * @Return: 用户
     * @Author: DING WEI
     * @Date: 2019-04-25 20:45
     * @Version: 1.0
     */
    UserDo findByUserName(String username);

    /**
     * 根据用户名和密码查询
     * @Params: username 用户名
     * @Params：password 密码
     * @Return: userDo
     * @Author: DING WEI
     * @Date: 2019-05-02 12:22
     * @Version: 1.0
     */
    UserDo findByUserNameAndPassword(@Param("username") String username,@Param("password") String password);
}
