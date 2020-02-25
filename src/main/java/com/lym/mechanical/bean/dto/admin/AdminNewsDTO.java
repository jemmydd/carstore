package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-25 11:42
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminNewsDTO {

    @ApiModelProperty(value = "海报id")
    private Integer id;

    @ApiModelProperty(value = "海报图片")
    private String image;

    @ApiModelProperty(value = "海报主题")
    private String type;

    @ApiModelProperty(value = "主题id")
    private Integer typeId;

    @ApiModelProperty(value = "是否生效，1生效，0不生效")
    private String isValid;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
