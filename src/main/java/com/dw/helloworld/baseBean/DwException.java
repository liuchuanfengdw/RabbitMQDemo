package com.dw.helloworld.baseBean;

import com.dw.helloworld.enums.ExceptionEnum;

/**
 * @Description: 自定义异常
 * @Author: DING WEI
 * @Date: 2019-04-03 21:53
 */
public class DwException extends RuntimeException {

    private Integer code;

    /**
     * 继承exception，加入错误状态值
     * @param exceptionEnum
     */
    public DwException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    public DwException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
