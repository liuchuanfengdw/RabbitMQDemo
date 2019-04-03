package com.dw.helloworld.service.impl;

import com.dw.helloworld.dao.UserDao;
import com.dw.helloworld.entity.dobean.UserDo;
import com.dw.helloworld.entity.dto.UserDto;
import com.dw.helloworld.entity.vo.UserVo;
import com.dw.helloworld.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Author: DING WEI
 * @Date: 2019-03-30 11:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public UserDo dto2Do(UserDto userDto){
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userDto,userDo);
        return userDo;
    }

    public UserVo do2Vo(UserDo userDo){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDo,userVo);
        return userVo;
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

    @Override
    public UserVo findByUserId(Long userId) {
        if(redisTemplate.opsForHash().hasKey("user",userId+"")){
            System.out.println("【走redis缓存取数据】");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<Object,Object> map = redisTemplate.opsForHash().entries("user");
                return objectMapper.convertValue(map,UserVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UserDo userDo = userDao.findById(userId);
        UserVo userVo = do2Vo(userDo);
        redisTemplate.opsForHash().put("user",userId+"",userVo);
        redisTemplate.expire("user",20, TimeUnit.MINUTES);
        return userVo;
    }


}
