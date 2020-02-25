package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-25 11:48
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminNewsTypeDTO {

    @ApiModelProperty(value = "主题id")
    private Integer id;

    @ApiModelProperty(value = "主题名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
