package com.dw.helloworld.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 用户dto
 * @Author: DING WEI
 * @Date: 2019-03-31 14:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("用户传输数据")
public class LoginDto implements Serializable {

    private static final long serialVersionUID = -6866912306569491255L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("设备号")
    private String cid;

}
