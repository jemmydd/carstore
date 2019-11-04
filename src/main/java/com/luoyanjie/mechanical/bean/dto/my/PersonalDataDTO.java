package com.luoyanjie.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-07-30 14:28
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataDTO {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("头像")
    private String headPortrait;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("省份编码")
    private String provinceCode;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("位置")
    private String location;
}
