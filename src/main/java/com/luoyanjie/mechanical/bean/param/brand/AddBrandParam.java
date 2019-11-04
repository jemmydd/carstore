package com.luoyanjie.mechanical.bean.param.brand;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-30 22:03
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBrandParam {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("首字母")
    private String capital;

    @ApiModelProperty("是否为热门：1热门、0不热门")
    private Byte isHot;
}
