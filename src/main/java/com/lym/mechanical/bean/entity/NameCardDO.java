package com.lym.mechanical.bean.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public class NameCardDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String name;

    private String mobile;

    private String wechatNo;

    private String companyName;

    private String jobTitle;

    private String companyAddress;

    private String location;

    private String introduce;

    private String voiceIntroduce;

    private Integer voiceIntroduceTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo == null ? null : wechatNo.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle == null ? null : jobTitle.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getVoiceIntroduce() {
        return voiceIntroduce;
    }

    public void setVoiceIntroduce(String voiceIntroduce) {
        this.voiceIntroduce = voiceIntroduce == null ? null : voiceIntroduce.trim();
    }

    public Integer getVoiceIntroduceTime() {
        return voiceIntroduceTime;
    }

    public void setVoiceIntroduceTime(Integer voiceIntroduceTime) {
        this.voiceIntroduceTime = voiceIntroduceTime;
    }
}