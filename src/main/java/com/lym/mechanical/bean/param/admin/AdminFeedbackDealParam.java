package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 15:41
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminFeedbackDealParam {

    @ApiModelProperty(value = "反馈id")
    private Integer id;

    @ApiModelProperty(value = "反馈内容")
    private String message;
}
