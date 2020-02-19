package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-19 16:32
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPublishSearchParam {

    @ApiModelProperty(value = "车商昵称")
    private String nickName;

    @ApiModelProperty(value = "车商手机号")
    private String mobile;

    @ApiModelProperty(value = "车商id")
    private String userId;

    @ApiModelProperty(value = "当前页，从1开始")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示数量")
    private Integer pageSize;
}
