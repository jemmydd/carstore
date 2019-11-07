package com.lym.mechanical.bean.param.publish;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-04 21:14
 * Coding happily every day!
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishComplexParam extends PublishParam {
    //定位省份，特殊场景下使用
    @ApiModelProperty("定位省份，特殊场景下使用")
    private String locationProvinceCode;
}
