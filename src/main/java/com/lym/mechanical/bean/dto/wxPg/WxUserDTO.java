package com.lym.mechanical.bean.dto.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-07-30 16:03
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUserDTO {
    private String openid;

    private String nickname;

    private String unionid;

    private Long errcode;

    private String errmsg;
}
