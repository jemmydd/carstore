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
public class BannerDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer bindId;

    private String image;

    private Byte isValid;

    private Integer userId;

    private String url;
}