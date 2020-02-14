package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-14 16:30
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "token")
    private String token;
}
