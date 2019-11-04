package com.luoyanjie.mechanical.bean.dto.purchaseInformation;

import com.luoyanjie.mechanical.bean.dto.OperateDTO;
import com.luoyanjie.mechanical.bean.dto.location.LocationDetailDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-24 20:33
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInformationDTO {
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("一级类目ID")
    private Integer categoryFirstId;

    @ApiModelProperty("一级类目名称")
    private String categoryFirstName;

    @ApiModelProperty("二级类目ID")
    private Integer categorySecondId;

    @ApiModelProperty("二级类目名称")
    private String categorySecondName;

    @ApiModelProperty("新老成都")
    private String newOldLevel;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("联系电话")
    private String contactMobile;

    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @ApiModelProperty("上下架状态")
    private Byte shelfStatus;

    @ApiModelProperty("创建者ID")
    private Integer creator;

    //****** START 处理过的数据
    @ApiModelProperty("友好的发布时间")
    private String createTimeFriendly;

    @ApiModelProperty("创建者信息")
    private UserDTO creatorInfo;

    @ApiModelProperty("省市区信息")
    private LocationDetailDTO locationDetail;

    @ApiModelProperty("操作按钮，在我的里面会有用到")
    private List<OperateDTO> operates;

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

    @ApiModelProperty("品牌名称")
    private String brandName;
}
