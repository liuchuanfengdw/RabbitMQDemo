package com.dw.helloworld.entity.dobean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 用户实体对象
 * @Author: DING WEI
 * @Date: 2019-03-31 14:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDo implements Serializable {

    private static final long serialVersionUID = 3592357977451229873L;

    private String id;

    private String username;

    private String password;

    private String faceImage;

    private String faceImageBig;

    private String nickName;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 客户端唯一编码（设备号）
     */
    private String cid;


}
