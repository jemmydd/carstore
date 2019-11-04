package com.luoyanjie.mechanical.bean.dto.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-15 10:47
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThisYearDataDistributionDTO {
    @ApiModelProperty("月份名")
    private String monthValue;

    @ApiModelProperty("注册用户数，包含所有的")
    private Integer userCount;

    @ApiModelProperty("发布数，包含所有的")
    private Integer publishCount;

    @ApiModelProperty("是否已经过了或者正在这个时间内")
    private Boolean overOrIn;
}
