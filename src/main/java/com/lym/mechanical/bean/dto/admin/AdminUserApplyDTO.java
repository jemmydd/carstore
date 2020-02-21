package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-21 17:31
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserApplyDTO {

    @ApiModelProperty(value = "申请记录id")
    private Integer id;

    @ApiModelProperty(value = "申请人头像")
    private String avatar;

    @ApiModelProperty(value = "申请人昵称")
    private String nickName;

    @ApiModelProperty(value = "申请人姓名")
    private String name;

    @ApiModelProperty(value = "申请人联系方式")
    private String mobile;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "申请时间")
    private String applyTime;
}
