package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-25 11:47
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminNewsTypeParam {

    @ApiModelProperty(value = "主题id，编辑时必传")
    private Integer id;

    @ApiModelProperty(value = "主题名称")
    private String name;
}
