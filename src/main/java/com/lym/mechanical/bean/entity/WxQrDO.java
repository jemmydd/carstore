package com.lym.mechanical.bean.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public class WxQrDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String md5Param;

    private String url;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMd5Param() {
        return md5Param;
    }

    public void setMd5Param(String md5Param) {
        this.md5Param = md5Param == null ? null : md5Param.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}