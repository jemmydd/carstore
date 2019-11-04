package com.luoyanjie.mechanical.bean.dto.banner;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-10 19:42
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerAdminDTO {
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("最新更新时间")
    private String updateTime;

    @ApiModelProperty("跳转的用户的ID")
    private Integer bindId;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("跳转地址")
    private String url;

    @ApiModelProperty("是否有效：1有效、0无效")
    private Byte isValid;

    @ApiModelProperty("绑定用户的信息")
    private UserDTO bind;
}
