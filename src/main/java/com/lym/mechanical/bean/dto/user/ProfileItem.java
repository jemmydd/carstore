package com.lym.mechanical.bean.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ProfileItem
 * @Description TODO
 * @Date 2020/3/17 10:44
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileItem {

    private String Tag;

    private String Value;
}
