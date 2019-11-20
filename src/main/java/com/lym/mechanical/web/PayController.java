package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.pay.VipPayDTO;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.pay.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Classname PayController
 * @Description
 * @Date 2019/11/19 19:02
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("pay")
@Api(tags = "支付")
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("list")
    @ApiOperation(value = "可购买列表")
    public Result<List<VipPayDTO>> payList(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(payService.payList(userId));
    }

    @GetMapping("pay")
    @ApiOperation(value = "微信预下单")
    public Result<Object> pay(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                              @RequestParam("type") @ApiParam(value = "购买类型") String type) {
        return ResultUtil.success(payService.pay(userId, type));
    }

    @PostMapping("callback")
    @ApiOperation(value = "微信支付回调")
    public String callback(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return payService.callback(httpServletRequest, httpServletResponse);
    }
}
