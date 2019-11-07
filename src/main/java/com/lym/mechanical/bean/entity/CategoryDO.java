package com.lym.mechanical.bean.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public class CategoryDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private Integer categoryLevel;

    private Integer parentId;

    private String name;

    private Integer sortedNum;

    private String icon;

    private Byte isShowIndex;

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

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSortedNum() {
        return sortedNum;
    }

    public void setSortedNum(Integer sortedNum) {
        this.sortedNum = sortedNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Byte getIsShowIndex() {
        return isShowIndex;
    }

    public void setIsShowIndex(Byte isShowIndex) {
        this.isShowIndex = isShowIndex;
    }
}