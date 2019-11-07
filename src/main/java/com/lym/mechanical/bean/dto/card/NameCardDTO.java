package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname NameCardDTO
 * @Description
 * @Date 2019/11/6 17:38
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameCardDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "职位")
    private String jobTitle;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "名片id")
    private Integer id;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "是否会员")
    private Boolean isVip;

    @ApiModelProperty(value = "是否认证车商")
    private Boolean isCarStore;

    @ApiModelProperty(value = "微信号")
    private String wechatNo;

    @ApiModelProperty(value = "公司位置经纬度")
    private String location;

    @ApiModelProperty(value = "名片所属用户id")
    private Integer userId;
}
