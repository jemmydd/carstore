package com.lym.mechanical.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer fromCarUserId;

    private Integer toCarUserId;

    private String content;

    private String type;

    private String userGroup;

    private Boolean isRead;

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

    public Integer getFromCarUserId() {
        return fromCarUserId;
    }

    public void setFromCarUserId(Integer fromCarUserId) {
        this.fromCarUserId = fromCarUserId;
    }

    public Integer getToCarUserId() {
        return toCarUserId;
    }

    public void setToCarUserId(Integer toCarUserId) {
        this.toCarUserId = toCarUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup == null ? null : userGroup.trim();
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}