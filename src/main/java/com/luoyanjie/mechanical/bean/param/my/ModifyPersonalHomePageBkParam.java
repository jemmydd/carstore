package com.luoyanjie.mechanical.bean.param.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-03 22:55
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPersonalHomePageBkParam {
    @ApiModelProperty("用户ID，公共参数")
    private Integer userId;

    @ApiModelProperty("背景")
    private String backgroundImage;
}
