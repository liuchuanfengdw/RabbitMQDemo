package com.dw.helloworld.service;

import com.dw.helloworld.entity.dobean.UserDo;
import com.dw.helloworld.entity.dto.UserDto;
import com.dw.helloworld.entity.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: DING WEI
 * @Data: 2019-03-30 11:18
 */
public interface UserService {

    Long saveUser(UserDto userDto);

    Integer saveBatch(List<UserDo> list);

    List<Map<String,Object>> findAllUser();

    UserVo findByUserId(Long userId);
}
