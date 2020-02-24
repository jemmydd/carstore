package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 16:30
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminVipDTO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "时段")
    private String days;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "是否限购")
    private String isLimit;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
