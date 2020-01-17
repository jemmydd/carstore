package com.lym.mechanical.bean.param.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GetMobileParam
 * @Description
 * @Date 2020/1/17 15:36
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMobileParam {

    private Integer userId;

    private String encryptedData;

    private String iv;

    private String code;
}
