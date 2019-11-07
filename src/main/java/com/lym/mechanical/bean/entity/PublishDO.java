package com.lym.mechanical.bean.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public class PublishDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private Integer userId;

    private Integer categoryFirstId;

    private Integer categorySecondId;

    private String phone;

    private String title;

    private String textIntroduce;

    private String voiceIntroduce;

    private Integer voiceIntroduceTime;

    private String mainMedia;

    private String contactPhone;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String areaCode;

    private String areaName;

    private String inPrice;

    private String outPrice;

    private Integer productiveYear;

    private Integer likeCount;

    private Integer viewCount;

    private Integer discussCount;

    private Integer collectionCount;

    private Integer viewPhoneCount;

    private Byte shelfStatus;

    private Integer brandId;

    private String usageHours;

    private Byte hasInvoice;

    private Byte hasCertificate;

    private String contact;

    private Integer carUserId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryFirstId() {
        return categoryFirstId;
    }

    public void setCategoryFirstId(Integer categoryFirstId) {
        this.categoryFirstId = categoryFirstId;
    }

    public Integer getCategorySecondId() {
        return categorySecondId;
    }

    public void setCategorySecondId(Integer categorySecondId) {
        this.categorySecondId = categorySecondId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTextIntroduce() {
        return textIntroduce;
    }

    public void setTextIntroduce(String textIntroduce) {
        this.textIntroduce = textIntroduce == null ? null : textIntroduce.trim();
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

    public String getMainMedia() {
        return mainMedia;
    }

    public void setMainMedia(String mainMedia) {
        this.mainMedia = mainMedia == null ? null : mainMedia.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getInPrice() {
        return inPrice;
    }

    public void setInPrice(String inPrice) {
        this.inPrice = inPrice == null ? null : inPrice.trim();
    }

    public String getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(String outPrice) {
        this.outPrice = outPrice == null ? null : outPrice.trim();
    }

    public Integer getProductiveYear() {
        return productiveYear;
    }

    public void setProductiveYear(Integer productiveYear) {
        this.productiveYear = productiveYear;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getDiscussCount() {
        return discussCount;
    }

    public void setDiscussCount(Integer discussCount) {
        this.discussCount = discussCount;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Integer getViewPhoneCount() {
        return viewPhoneCount;
    }

    public void setViewPhoneCount(Integer viewPhoneCount) {
        this.viewPhoneCount = viewPhoneCount;
    }

    public Byte getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Byte shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getUsageHours() {
        return usageHours;
    }

    public void setUsageHours(String usageHours) {
        this.usageHours = usageHours == null ? null : usageHours.trim();
    }

    public Byte getHasInvoice() {
        return hasInvoice;
    }

    public void setHasInvoice(Byte hasInvoice) {
        this.hasInvoice = hasInvoice;
    }

    public Byte getHasCertificate() {
        return hasCertificate;
    }

    public void setHasCertificate(Byte hasCertificate) {
        this.hasCertificate = hasCertificate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public Integer getCarUserId() {
        return carUserId;
    }

    public void setCarUserId(Integer carUserId) {
        this.carUserId = carUserId;
    }
}