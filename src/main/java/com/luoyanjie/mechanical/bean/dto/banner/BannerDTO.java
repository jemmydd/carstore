package com.luoyanjie.mechanical.bean.dto.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-10 19:02
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO {
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("是否可以跳转到某个用户的主页")
    private Boolean canJump;

    @ApiModelProperty("跳转的用户的ID")
    private Integer bindId;

    //190926添加的
    @ApiModelProperty("跳转地址，输入什么返回什么")
    private String url;
}
