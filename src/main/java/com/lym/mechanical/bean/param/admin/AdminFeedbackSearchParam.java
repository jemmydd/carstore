package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 15:39
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminFeedbackSearchParam {

    @ApiModelProperty(value = "开始时间，yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty(value = "开始时间，yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty(value = "当前页，从1开始")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示数量")
    private Integer pageSize;

    @ApiModelProperty(value = "是否处理，0-未处理，1-已处理")
    private String isDeal;
}
