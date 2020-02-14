package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-14 16:34
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginParam {

    @ApiModelProperty(value = "账户名", required = true)
    private String account;

    @ApiModelProperty(value = "md5加密后的密码", required = true)
    private String password;
}
