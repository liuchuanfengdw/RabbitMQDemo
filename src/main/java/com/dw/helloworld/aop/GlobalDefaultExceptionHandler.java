package com.dw.helloworld.aop;

import com.dw.helloworld.baseBean.DwException;
import com.dw.helloworld.baseBean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description: 统一异常处理
 * @Author: DING WEI
 * @Date: 2019-04-05 20:23
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public ResultBean exceptionHandler(HttpServletRequest request,Exception e){
        Throwable throwable = getBusinessException(e);
        if (!Objects.isNull(throwable)) {
            return new ResultBean().faild("操作失败",throwable.getCause());
        }
        return new ResultBean().faild("操作失败","服务器正忙,稍后重试");
    }


    public Throwable getBusinessException(Throwable e){
        if(e == null){
            return null;
        }else if(e instanceof DwException){
            e.printStackTrace();
            Throwable temp = getBusinessException(e.getCause());
            if (temp == null) {
                return e;
            } else {
                return temp;
            }
        }else{
            e.printStackTrace();
            return getBusinessException(e.getCause());
        }
    }
}
