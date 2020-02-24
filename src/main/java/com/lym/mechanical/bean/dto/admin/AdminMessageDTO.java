package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-24 16:41
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminMessageDTO {

    @ApiModelProperty(value = "发送内容")
    private String content;

    @ApiModelProperty(value = "发送对象")
    private String target;

    @ApiModelProperty(value = "发送时间")
    private String createTime;
}
