package com.luoyanjie.mechanical.bean.param.cooperationCompany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-17 19:41
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCooperationCompanyParam {
    private String image;

    private String name;

    private String bizIntroduce;

    private String address;

    private String contactPhone;

    private Integer userId;
}
