package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liyimin
 * @create 2020-02-14 16:39
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatisticDTO {

    @ApiModelProperty(value = "总用户量")
    private Integer totalCarUserCount;

    @ApiModelProperty(value = "总设备量")
    private Integer totalPublishCount;

    @ApiModelProperty(value = "年度数据分析")
    private List<AdminStatisticYearDTO> adminStatisticYear;

    @ApiModelProperty(value = "月数据分析")
    private List<AdminStatisticMonthDTO> adminStatisticMonth;
}
