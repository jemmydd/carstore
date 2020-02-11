package com.lym.mechanical.bean.dto.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liyimin
 * @create 2020-02-11 12:21
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonDTO {

    private Integer userId;

    private Date createTime;
}
