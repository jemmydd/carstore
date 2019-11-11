package com.lym.mechanical.bean.dto.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MessagePublishDTO
 * @Description
 * @Date 2019/11/11 10:35
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessagePublishDTO {

    @ApiModelProperty(value = "设备图片")
    private String image;

    @ApiModelProperty(value = "设备标题")
    private String title;

    @ApiModelProperty(value = "设备发布日期")
    private String date;

    @ApiModelProperty(value = "设备价格")
    private String price;
}
