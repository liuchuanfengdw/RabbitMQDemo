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
public class UserVo implements Serializable {

    private static final long serialVersionUID = -4043583975810906988L;

    private Long id;

    private String name;

    private Integer age;

}
