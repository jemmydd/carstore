package com.lym.mechanical.bean.param.publish;

import com.lym.mechanical.bean.enumBean.PublishCallSceneEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-03 10:15
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishParam {
    @ApiModelProperty("当前用户id")
    private Integer userId;

    @ApiModelProperty("页码，必传")
    private Integer pageNum;

    @ApiModelProperty("页大小，必传")
    private Integer pageSize;

    @ApiModelProperty("搜索的文本内容")
    private String searchText;

    @ApiModelProperty("省份编码")
    private String provinceCode;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("一级类目")
    private Integer categoryFirstId;

    @ApiModelProperty("二级类目")
    private Integer categorySecondId;

    @ApiModelProperty("排序规则")
    private String sortedType;

    @ApiModelProperty(value = "调用场景", hidden = true)
    private PublishCallSceneEnum publishCallSceneEnum;

    @ApiModelProperty("车商用户id")
    private Integer publishId;

    @ApiModelProperty("是否已经下架")
    private Byte isDownShelf;

    //****** START on 191010
    @ApiModelProperty("品牌ID")
    private Integer brandId;
}
