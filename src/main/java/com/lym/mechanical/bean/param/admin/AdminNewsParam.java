package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-25 11:45
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminNewsParam {

    @ApiModelProperty(value = "海报id，编辑时必传")
    private Integer id;

    @ApiModelProperty(value = "海报图片")
    private String image;

    @ApiModelProperty(value = "主题id")
    private Integer typeId;

    @ApiModelProperty(value = "是否生效，1-生效，0-不生效")
    private String isValid;
}
