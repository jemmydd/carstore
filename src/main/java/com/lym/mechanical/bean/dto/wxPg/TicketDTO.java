package com.lym.mechanical.bean.dto.wxPg;

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
public class TicketDTO {
    private Integer errcode;

    private String errmsg;

    private String ticket;

    private Integer expires_in;
}

