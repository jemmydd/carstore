package com.luoyanjie.mechanical.bean.param.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-10-11 10:17
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateSendDataParam {
    private String key;

    private String value;
}
