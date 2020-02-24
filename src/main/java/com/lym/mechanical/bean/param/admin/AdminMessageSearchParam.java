package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 15:20
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminMessageSearchParam {

    @ApiModelProperty(value = "开始时间，yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty(value = "开始时间，yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty(value = "当前页，从1开始")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示数量")
    private Integer pageSize;
}
