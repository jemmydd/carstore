package com.luoyanjie.mechanical.bean.param.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-12 21:27
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBannerParam {
    @ApiModelProperty("创建者")
    private Integer userId;

    @ApiModelProperty("banner的id")
    private Integer id;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("绑定的用户")
    private Integer bindId;

    @ApiModelProperty("是否生效")
    private Boolean isValid;

    //190926添加的
    @ApiModelProperty("跳转地址，输入什么返回什么")
    private String url;
}
