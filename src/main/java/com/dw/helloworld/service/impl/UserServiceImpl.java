package com.dw.helloworld.service.impl;

import com.dw.helloworld.baseBean.ResultBean;
import com.dw.helloworld.dao.UserDao;
import com.dw.helloworld.entity.dobean.UserDo;
import com.dw.helloworld.entity.dto.LoginDto;
import com.dw.helloworld.entity.vo.LoginVo;
import com.dw.helloworld.service.UserService;
import com.dw.helloworld.utils.MD5Utils;
import com.dw.helloworld.utils.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用户业务层
 * @Author: DING WEI
 * @Date: 2019-03-30 11:19
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserDao userDao;

    @Resource(name = "dwRedisTemplate")
    private RedisTemplate redisTemplate;

    public UserDo dto2Do(LoginDto loginDto){
        UserDo userDo = new UserDo();
        if(loginDto != null){
            BeanUtils.copyProperties(loginDto,userDo);
            return userDo;
        }
        return null;
    }

    public LoginVo do2Vo(UserDo userDo){
        LoginVo loginVo = new LoginVo();
        if(userDo != null){
            BeanUtils.copyProperties(userDo, loginVo);
            return loginVo;
        }
        return null;
    }

    @Override
    public UserDo saveUser(LoginDto loginDto) {
        UserDo userDo = dto2Do(loginDto);
        // 获取全局递增id
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        userDo.setId(String.valueOf(idWorker.nextId()));
        // TODO 生成二维码
        userDo.setQrCode("");
        // nickname
        userDo.setNickName(loginDto.getUsername());
        // face_image
        userDo.setFaceImage("");
        userDo.setFaceImageBig("");
        userDao.save(userDo);
        return userDo;
    }

    @Override
    public Integer saveBatch(List<UserDo> list) {
        return userDao.saveBatch(list);
    }


    @Override
    public List<Map<String, Object>> findAllUser() {
        return userDao.findAll();
    }

    @Override
    public LoginVo findByUserId(String userId) {
        if(redisTemplate.opsForHash().hasKey("user",userId+"")){
            logger.info("【走redis缓存取数据】");
            LoginVo loginVo = (LoginVo) redisTemplate.opsForHash().get("user",userId+"");
            return loginVo;
        }
        UserDo userDo = userDao.findById(userId);
        LoginVo loginVo = do2Vo(userDo);
        redisTemplate.opsForHash().put("user",userId+"", loginVo);
        redisTemplate.expire("user",20, TimeUnit.MINUTES);
        return loginVo;
    }

    @Override
    public LoginVo findByUserName(String username) {
        UserDo userDo = userDao.findByUserName(username);
        LoginVo loginVo = do2Vo(userDo);
        return loginVo;
    }

    @Override
    public ResultBean registOrLogin(LoginDto loginDto) throws Exception {
        LoginVo loginVo = findByUserName(loginDto.getUsername());
        if(loginVo != null){
            // 登陆校验
            UserDo userDo = userDao.findByUserNameAndPassword(loginDto.getUsername(),MD5Utils.getMD5Str(loginDto.getPassword()));
            if(userDo != null){
                logger.info(loginDto.getUsername() + "--> 登陆成功");
                return new ResultBean().success("登陆成功", loginVo);
            }else{
                logger.info(loginDto.getUsername() + "--> 用户名或密码错误");
                return new ResultBean().faild("用户名或密码错误");
            }
        }else{
            // 注册
            loginDto.setPassword(MD5Utils.getMD5Str(loginDto.getPassword()));
            UserDo userDo = this.saveUser(loginDto);
            logger.info(loginDto.getUsername() + "--> 注册成功");
            return new ResultBean().success("注册成功",do2Vo(userDo));
        }
    }

    @Override
    public ResultBean myFriends(String userId) {
        return null;
    }


}
