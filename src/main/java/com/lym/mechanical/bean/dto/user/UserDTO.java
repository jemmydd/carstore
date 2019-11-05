package com.lym.mechanical.bean.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UserDTO
 * @Description
 * @Date 2019/11/5 15:16
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String phone;

    private String openId;

    private String sessionKey;
}
