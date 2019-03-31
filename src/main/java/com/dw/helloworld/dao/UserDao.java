package com.dw.helloworld.dao;

import com.dw.helloworld.entity.dobean.UserDo;
import org.apache.ibatis.annotations.Mapper;

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

    List<Map<String,Object>> findAll();


}
