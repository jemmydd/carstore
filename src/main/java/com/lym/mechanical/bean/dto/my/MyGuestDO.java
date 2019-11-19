package com.lym.mechanical.bean.dto.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Classname MyGuestDO
 * @Description
 * @Date 2019/11/18 10:43
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyGuestDO {

    private Integer userId;

    private Date createTime;
}
