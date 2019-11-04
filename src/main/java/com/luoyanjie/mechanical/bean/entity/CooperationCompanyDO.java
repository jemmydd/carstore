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
public class CooperationCompanyDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String image;

    private String name;

    private String bizIntroduce;

    private String address;

    private String contactPhone;

    private Integer creator;
}