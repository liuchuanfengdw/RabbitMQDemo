package com.dw.helloworld.service.impl;

import com.dw.helloworld.dao.UserDao;
import com.dw.helloworld.entity.dobean.UserDo;
import com.dw.helloworld.entity.dto.UserDto;
import com.dw.helloworld.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: DING WEI
 * @Date: 2019-03-30 11:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public UserDo dto2Do(UserDto userDto){
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userDto,userDo);
        return userDo;
    }

    @Override
    public Long saveUser(UserDto userDto) {
        UserDo userDo = dto2Do(userDto);
        userDao.save(userDo);
        return userDo.getId();
    }

    @Override
    public List<Map<String, Object>> findAllUser() {
        return userDao.findAll();
    }
}
