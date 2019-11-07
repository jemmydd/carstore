package com.lym.mechanical.bean.dto.wxPg;

import com.google.common.collect.Lists;
import com.lym.mechanical.bean.enumBean.OperateTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateDTO {
    @ApiModelProperty("排序码")
    private Integer sortedNum;

    @ApiModelProperty("按钮名称")
    private String name;

    @ApiModelProperty("按钮类型")
    private String type;

    private static OperateDTO getOperate(OperateTypeEnum operateTypeEnum, Integer sortedNum) {
        return OperateDTO.builder().name(operateTypeEnum.getText()).sortedNum(sortedNum).type(operateTypeEnum.name()).build();
    }

    public static List<OperateDTO> getOperate(OperateTypeEnum... operateTypeEnums) {
        List<OperateDTO> data = Lists.newArrayList();

        if (!ObjectUtils.isEmpty(operateTypeEnums)) {
            int index = 1;
            for (OperateTypeEnum operateTypeEnum : operateTypeEnums) {
                data.add(getOperate(operateTypeEnum, index));
                index++;
            }
        }

        return data;
    }
}
