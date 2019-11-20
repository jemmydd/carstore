package com.lym.mechanical.bean.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname VipPayDTO
 * @Description
 * @Date 2019/11/19 19:03
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VipPayDTO {

    @ApiModelProperty(value = "是否限时")
    private Boolean isTimeLimit;

    @ApiModelProperty(value = "购买类型")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "金额")
    private String amount;

    @ApiModelProperty(value = "描述")
    private String desc;
}
