package com.luoyanjie.mechanical.bean.dto.cooperationCompany;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-17 19:25
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CooperationCompanyDTO {
    @ApiModelProperty("合作公司的ID")
    private Integer id;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("公司的图片")
    private String image;

    @ApiModelProperty("公司名称")
    private String name;

    @ApiModelProperty("业务介绍")
    private String bizIntroduce;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系电话")
    private String contactPhone;
}
