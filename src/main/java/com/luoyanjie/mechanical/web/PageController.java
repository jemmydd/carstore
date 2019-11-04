package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.param.wxPg.QrParam;
import com.luoyanjie.mechanical.service.wxPg.WxPgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * @author luoyanjie
 * @date 2019-09-19 15:18
 * Coding happily every day!
 */
@Slf4j
@Controller
@RequestMapping("page")
public class PageController {
    @Autowired
    private WxPgService wxPgService;

    @GetMapping("qr")
    public ModelAndView qr() {
        ModelAndView modelAndView = new ModelAndView("/qr");
        modelAndView.addObject("qrSrc", wxPgService.getQr(QrParam.builder().scene("ceshi").build()));
        return modelAndView;
    }

    @GetMapping("qrNew")
    public ModelAndView qrNew() {
        ModelAndView modelAndView = new ModelAndView("/qr");
        modelAndView.addObject("qrSrc", wxPgService.getQr(QrParam.builder().scene(UUID.randomUUID().toString().replaceAll("-", "")).build()));
        return modelAndView;
    }
}
