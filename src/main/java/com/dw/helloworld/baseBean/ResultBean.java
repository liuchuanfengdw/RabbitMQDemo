package com.dw.helloworld.baseBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 统一返回前端对象
 * @Author: DING WEI
 * @Date: 2019-03-30 18:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T> {

    private Integer code;

    private String msg;

    private T data;
}
