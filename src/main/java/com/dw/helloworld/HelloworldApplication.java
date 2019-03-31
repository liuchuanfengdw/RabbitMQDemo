package com.dw.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 * @Author: DING WEI
 * @Date: 2019-03-24 16:18
 * @Version: 1.0
 */
@SpringBootApplication
@EnableSwagger2
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }

}
