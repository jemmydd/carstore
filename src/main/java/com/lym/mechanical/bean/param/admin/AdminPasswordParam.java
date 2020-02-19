package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-19 16:05
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPasswordParam {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "旧密码，md5加密后的")
    private String oldPassword;

    @ApiModelProperty(value = "新密码，md5加密后的")
    private String newPassword;

    @ApiModelProperty(value = "再次新密码，md5加密后的")
    private String newPasswordAgain;
}
