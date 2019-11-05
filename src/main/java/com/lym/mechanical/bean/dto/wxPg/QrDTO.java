package com.lym.mechanical.bean.dto.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-03 20:37
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrDTO {
    private Integer errcode;

    private String errmsg;

    private String contentType;

    private String buffer;
}
