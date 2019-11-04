package com.luoyanjie.mechanical.bean.param.purchaseInformation;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-24 20:28
 * Coding happily every day!
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPurchaseInformationParam {
    @ApiModelProperty("发布求购信息的ID")
    private Integer creator;

    @ApiModelProperty("一级类目")
    private Integer firstCategoryId;

    @ApiModelProperty("二级类目")
    private Integer secondCategoryId;

    @ApiModelProperty("省份编码")
    private String provinceCode;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("新旧程度")
    private String newOldLevel;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("是否下架，上架状态：1出售中、0已下架")
    private Byte shelfStatus;

    //20190925添加的
    @ApiModelProperty("期望价格")
    private String expectedPrice;

    @ApiModelProperty("使用小时数")
    private String usageHours;

    @ApiModelProperty("年限")
    private String yearLimitNum;

    @ApiModelProperty("是否有发票：1有、0无")
    private Byte hasInvoice;

    @ApiModelProperty("是否有合格证：1有、0无")
    private Byte hasCertificate;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("备注")
    private String remark;

    //****** START on 191010
    @ApiModelProperty("品牌ID")
    private Integer brandId;
}
