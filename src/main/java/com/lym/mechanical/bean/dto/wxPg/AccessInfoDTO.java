package com.lym.mechanical.bean.dto.wxPg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-03 19:49
 * Coding happily every day!
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccessInfoDTO {
    @ApiModelProperty("accessToken")
    private String accessToken;
}
