package com.luoyanjie.mechanical.bean.param.purchaseInformation;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-24 20:30
 * Coding happily every day!
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPurchaseInformationParam extends AddPurchaseInformationParam {
    @ApiModelProperty("求购信息的ID")
    private Integer id;
}
