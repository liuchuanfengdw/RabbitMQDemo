package com.dw.helloworld.rest;

import com.dw.helloworld.utils.FdfsUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @description: TODO
 * @author: DING WEI
 * @date: 2019-05-04 20:08
 */
@RestController
@RequestMapping("/file")
public class FdfsController {

    @Resource
    private FdfsUtil fdfsUtil;

    @PostMapping("/upload_file")
    @ApiOperation("上传文件")
    public String uploadFile(MultipartFile file){
        try {
            String url = fdfsUtil.uploadFile(file);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
