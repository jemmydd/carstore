package com.lym.mechanical.bean.param.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-20 15:55
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCarStoreSearchParam {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "是-1，否-0，不传代表全部")
    private String isVip;

    @ApiModelProperty(value = "当前页，从1开始")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示数量")
    private Integer pageSize;
}
