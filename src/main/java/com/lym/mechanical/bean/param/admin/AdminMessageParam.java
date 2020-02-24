package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 16:42
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminMessageParam {

    @ApiModelProperty(value = "发送内容")
    private String content;

    @ApiModelProperty(value = "发送对象，多个用,隔开，不传代表全部")
    private String userIds;
}
