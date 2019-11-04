package com.luoyanjie.mechanical.bean.dto.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-07-30 16:00
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPgAuthDTO {
    private String openid;

    private String session_key;

    private String unionid;

    private Long errcode;

    private String errmsg;
}
