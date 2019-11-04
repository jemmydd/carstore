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
public class PublishImageVideoDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer publishId;

    private String file;

    private String type;

    private String firstPic;
}