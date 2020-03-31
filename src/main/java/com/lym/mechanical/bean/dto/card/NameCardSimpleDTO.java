package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname NameCardSimpleDTO
 * @Description
 * @Date 2019/11/7 16:29
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameCardSimpleDTO {

    @ApiModelProperty(value = "名片id")
    private Integer cardId;

    @ApiModelProperty(value = "名片编号")
    private String code;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "是否已经添加")
    private Boolean hasAdd;

    @ApiModelProperty(value = "职位")
    private String jobTitle;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "是否vip")
    private Boolean isVip;
}
