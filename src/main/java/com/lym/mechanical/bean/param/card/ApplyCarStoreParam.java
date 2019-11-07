package com.lym.mechanical.bean.param.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ApplyCarStoreParam
 * @Description
 * @Date 2019/11/7 13:47
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyCarStoreParam {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "备注")
    private String remark;
}
