package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-20 15:57
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminVipParam {

    @ApiModelProperty(value = "要开通的用户id")
    private Integer userId;

    @ApiModelProperty(value = "要开通的天数")
    private Integer days;
}
