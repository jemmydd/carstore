package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-21 16:10
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminFriendDTO {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "职务")
    private String jobTitle;

    @ApiModelProperty(value = "累计访客")
    private Integer totalGuest;
}
