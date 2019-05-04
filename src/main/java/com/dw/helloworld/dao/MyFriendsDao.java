package com.dw.helloworld.dao;

import com.dw.helloworld.entity.dobean.MyFriendsDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 我的好友
 * @author: DING WEI
 * @date: 2019-05-03 09:09
 */
@Mapper
public interface MyFriendsDao {

    List<MyFriendsDo> findMyFriends(String userId);

}
