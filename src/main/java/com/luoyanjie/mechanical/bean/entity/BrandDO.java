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
public class BrandDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String name;

    private String capital;

    private Byte isHot;
}