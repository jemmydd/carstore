package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-14 16:55
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatisticMonthDTO {

    @ApiModelProperty(value = "日期")
    private String day;

    @ApiModelProperty(value = "当日用户量")
    private Integer userCount;

    @ApiModelProperty(value = "当日设备量")
    private Integer publishCount;
}
