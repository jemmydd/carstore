package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 15:17
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminBuyDTO {

    @ApiModelProperty(value = "购买记录id")
    private Integer id;

    @ApiModelProperty(value = "购买人信息")
    private CarUserDTO user;

    @ApiModelProperty(value = "开通天数")
    private String days;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "购买方式")
    private String buyType;

    @ApiModelProperty(value = "购买时间")
    private String buyTime;
}
