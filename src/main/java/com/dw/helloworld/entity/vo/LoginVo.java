package com.dw.helloworld.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: DING WEI
 * @Date: 2019-04-02 21:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo implements Serializable {

    private static final long serialVersionUID = -4043583975810906988L;

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
