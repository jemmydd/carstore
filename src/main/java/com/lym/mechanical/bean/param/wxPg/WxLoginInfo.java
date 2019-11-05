package com.lym.mechanical.bean.param.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginInfo {
    private String code;

    private UserInfo userInfo;
}
