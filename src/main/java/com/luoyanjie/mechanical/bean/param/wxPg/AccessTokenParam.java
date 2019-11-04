package com.luoyanjie.mechanical.bean.param.wxPg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-03 19:53
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenParam {
    @ApiModelProperty(value = "appId", required = true)
    private String appId;

    @ApiModelProperty(value = "appSecret", required = true)
    private String appSecret;
}
