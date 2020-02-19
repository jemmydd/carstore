package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-19 17:08
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPublishRecordDTO {

    @ApiModelProperty(value = "设备主图")
    private String image;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "设备型号")
    private String type;

    @ApiModelProperty(value = "浏览时间/收藏时间")
    private String time;

    @ApiModelProperty(value = "浏览人/收藏人信息")
    private CarUserDTO user;
}
