package com.luoyanjie.mechanical.bean.dto.wxPg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-09-02 22:28
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUserPhoneDTO {
    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;
}
