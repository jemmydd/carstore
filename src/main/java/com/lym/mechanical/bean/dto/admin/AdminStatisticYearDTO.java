package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-14 16:40
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatisticYearDTO {

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "当月用户量")
    private Integer userCount;

    @ApiModelProperty(value = "当月设备量")
    private Integer publishCount;
}
