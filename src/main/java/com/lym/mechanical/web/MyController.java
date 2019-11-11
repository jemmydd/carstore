package com.lym.mechanical.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname MyController
 * @Description
 * @Date 2019/11/11 13:49
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("my")
@Api(tags = "我的")
public class MyController {

    private String name;

    private Integer cardId;

    private String avatar;

    private String vipEndTime;

    private Boolean isCarStore;

    private Boolean isVip;

    private Integer todayGuest;

//    private Integer
}
