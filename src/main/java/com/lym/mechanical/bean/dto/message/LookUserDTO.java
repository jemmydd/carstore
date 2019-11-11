package com.lym.mechanical.bean.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Classname LookUserDTO
 * @Description
 * @Date 2019/11/11 11:23
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookUserDTO {

    private Integer userId;

    private Integer bizId;

    private Date date;

    private String bizType;
}
