package com.luoyanjie.mechanical.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer fromId;

    private Integer toId;
}