package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-21 17:39
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminApplyOperateParam {

    @ApiModelProperty(value = "申请id")
    private Integer id;

    @ApiModelProperty(value = "操作类型，0-拒绝，1-通过")
    private String type;

    @ApiModelProperty(value = "拒绝原因")
    private String reason;
}
