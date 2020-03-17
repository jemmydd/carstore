package com.lym.mechanical.bean.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname UserSetInfoDTO
 * @Description TODO
 * @Date 2020/3/17 10:43
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSetInfoDTO {

    private String From_Account;

    private List<ProfileItem> ProfileItem;
}
