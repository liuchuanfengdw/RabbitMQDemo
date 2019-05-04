package com.dw.helloworld;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 * @Author: DING WEI
 * @Date: 2019-03-24 16:18
 * @Version: 1.0
 */
@SpringBootApplication
@EnableSwagger2
@Import(FdfsClientConfig.class)
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }

}
