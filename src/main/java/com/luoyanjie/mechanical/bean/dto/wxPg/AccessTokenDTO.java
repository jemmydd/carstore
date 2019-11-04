package com.luoyanjie.mechanical.bean.dto.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-03 19:57
 * Coding happily every day!
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccessTokenDTO {
    private String access_token;

    private Integer expires_in;
}

