package com.dw.helloworld.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserDto implements Serializable {

    private static final long serialVersionUID = -6866912306569491255L;

    @JsonIgnore
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private Integer age;

}
