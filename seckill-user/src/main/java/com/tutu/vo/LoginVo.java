package com.tutu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author zhangBoKai
 * @Date 2022/1/14 13:38
 */
@Data
@ApiModel("登录")
public class LoginVo implements Serializable {

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty("用户名，注册时需要")
    private String UserName;
}
