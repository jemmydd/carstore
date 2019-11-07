package com.lym.mechanical.bean.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public class DiscussDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String content;

    private Integer publishId;

    private Integer publishUserId;

    private Byte publishUserReadStatus;

    private Integer floorDiscussId;

    private Integer floorDiscussUserId;

    private Integer beReplyDiscussId;

    private Integer beReplyReplyDiscussUserId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Integer publishUserId) {
        this.publishUserId = publishUserId;
    }

    public Byte getPublishUserReadStatus() {
        return publishUserReadStatus;
    }

    public void setPublishUserReadStatus(Byte publishUserReadStatus) {
        this.publishUserReadStatus = publishUserReadStatus;
    }

    public Integer getFloorDiscussId() {
        return floorDiscussId;
    }

    public void setFloorDiscussId(Integer floorDiscussId) {
        this.floorDiscussId = floorDiscussId;
    }

    public Integer getFloorDiscussUserId() {
        return floorDiscussUserId;
    }

    public void setFloorDiscussUserId(Integer floorDiscussUserId) {
        this.floorDiscussUserId = floorDiscussUserId;
    }

    public Integer getBeReplyDiscussId() {
        return beReplyDiscussId;
    }

    public void setBeReplyDiscussId(Integer beReplyDiscussId) {
        this.beReplyDiscussId = beReplyDiscussId;
    }

    public Integer getBeReplyReplyDiscussUserId() {
        return beReplyReplyDiscussUserId;
    }

    public void setBeReplyReplyDiscussUserId(Integer beReplyReplyDiscussUserId) {
        this.beReplyReplyDiscussUserId = beReplyReplyDiscussUserId;
    }
}