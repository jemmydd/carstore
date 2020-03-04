package com.lym.mechanical.service.pay;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.common.WXPayConstants;
import com.lym.mechanical.bean.dto.pay.*;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.PaymentDO;
import com.lym.mechanical.bean.entity.VipConfigDO;
import com.lym.mechanical.bean.entity.VipOrderDO;
import com.lym.mechanical.bean.entity.VipRecordDO;
import com.lym.mechanical.bean.enumBean.VipTypeEnum;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.PaymentDOMapper;
import com.lym.mechanical.dao.mapper.VipConfigDOMapper;
import com.lym.mechanical.dao.mapper.VipOrderDOMapper;
import com.lym.mechanical.dao.mapper.VipRecordDOMapper;
import com.lym.mechanical.sys.PgAppInfo;
import com.lym.mechanical.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname PayService
 * @Description
 * @Date 2019/11/19 19:09
 * @Created by jimy
 * good good code, day day up!
 */
@Service
@Slf4j
public class PayService {

    private static final BigDecimal[] AMOUNT = {BigDecimal.ZERO, new BigDecimal(1), new BigDecimal(39), new BigDecimal(98), new BigDecimal(288)};

    @Autowired
    private PgAppInfo pgAppInfo;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private VipOrderDOMapper vipOrderDOMapper;

    @Autowired
    private PaymentDOMapper paymentDOMapper;

    @Autowired
    private VipConfigDOMapper vipConfigDOMapper;

    @Autowired
    private VipRecordDOMapper vipRecordDOMapper;

    public List<VipPayDTO> payList(Integer userId) {
        List<VipPayDTO> result = Lists.newArrayList();
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        List<VipConfigDO> configDOS = vipConfigDOMapper.selectAll();
        if (!carUserDO.getHasTry()) {
            result.addAll(configDOS.stream().filter(row -> row.getIsLimit()).map(row -> VipPayDTO.builder()
                    .amount(row.getPrice().toString())
                    .desc(row.getDescription())
                    .isTimeLimit(Boolean.TRUE)
                    .title(getDays(row.getDays()))
                    .type(row.getId().toString())
                    .build())
            .collect(Collectors.toList()));
        }
        result.addAll(configDOS.stream().filter(row -> !row.getIsLimit()).map(row -> VipPayDTO.builder()
                .amount(row.getPrice().toString())
                .desc(row.getDescription())
                .isTimeLimit(Boolean.FALSE)
                .title(getDays(row.getDays()))
                .type(row.getId().toString())
                .build())
                .collect(Collectors.toList()));
        return result;
    }

    @Transactional
    public Object pay(Integer userId, String type) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO) || StringUtils.isEmpty(carUserDO.getOpenid())) {
            throw new RuntimeException("用户不存在");
        }
        VipConfigDO vipConfigDO = vipConfigDOMapper.selectByPrimaryKey(Integer.parseInt(type));
        if (Objects.isNull(vipConfigDO)) {
            throw new RuntimeException("购买类型有误");
        }
        Date now = DateUtil.now();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        BigDecimal amount = vipConfigDO.getPrice();

        PaymentDO paymentDO = PaymentDO.builder()
                .createTime(now)
                .updateTime(now)
                .uuid(uuid)
                .amount(amount)
                .status("NO_PAY")
                .build();
        paymentDOMapper.insertSelective(paymentDO);

        // 创建订单
        VipOrderDO orderDO = VipOrderDO.builder()
                .createTime(now)
                .updateTime(now)
                .userId(userId)
                .buyType(Byte.valueOf(type))
                .payType("WX")
                .oughtAmount(amount)
                .paymentId(paymentDO.getId())
                .status("NO_PAY")
                .build();
        vipOrderDOMapper.insertSelective(orderDO);

        Integer orderId = orderDO.getId();
        if (orderId == null || orderId <= 0) {
            throw new RuntimeException("订单创建失败");
        }

        PassBackP passBackP = PassBackP.builder()
                .orderId(orderId)
                .build();//额外待的参数

        String payTitle = "购买会员";
        Object result;
        try {
            result = paramParamByWxPg(amount, payTitle, paymentDO, passBackP, carUserDO.getOpenid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信付款参数创建失败");
        }
        return result;
    }

    private Object paramParamByWxPg(BigDecimal fpa, String payTitle, PaymentDO payment, PassBackP passBackP, String openId)
            throws Exception {
        Map<String, String> unifiedOrderParam = getWxUnifiedOrderParamPg(
                fpa, payTitle, passBackP, pgAppInfo.getAppId(), pgAppInfo.getMchId(), payment.getUuid(), openId, pgAppInfo.getServerIp(), pgAppInfo.getCallbackUrl());
        unifiedOrderParam.put("sign", WXPayUtil.generateSignature(unifiedOrderParam, pgAppInfo.getPrivateKey(), com.github.wxpay.sdk.WXPayConstants.SignType.MD5));

        WXPay wxPay = new WXPay(MyWxPayConfigImpl.builder().key(pgAppInfo.getPrivateKey()).build());

        Map<String, String> responseMap = wxPay.processResponseXml(
                wxPay.requestWithoutCert(WXPayConstants.UNIFIEDORDER_URL_SUFFIX, unifiedOrderParam, 6 * 1000, 8 * 1000)
        );//统一下单请求

        if (Objects.equals(responseMap.remove("return_code"), "FAIL") || Objects.equals(responseMap.remove("result_code"), "FAIL")) {
            throw new RuntimeException("统一下单请求异常");
        }

        Map<String, String> result = Maps.newHashMap();

        result.put("appId", pgAppInfo.getAppId());
        result.put("signType", "MD5");
        result.put("package", "prepay_id=" + responseMap.get("prepay_id"));
        result.put("nonceStr", payment.getUuid());
        result.put("timeStamp", String.valueOf(DateUtil.getUnixTimestamp()));
        result.put("paySign", WXPayUtil.generateSignature(result, pgAppInfo.getPrivateKey(), com.github.wxpay.sdk.WXPayConstants.SignType.MD5));
        return result;
    }

    public static Map<String, String> getWxUnifiedOrderParamPg(BigDecimal pa, String payTitle, PassBackP attach,
                                                               String appId, String mchId, String uuid, String openId,
                                                               String serverIp, String notifyUrl) {

        return ObjectTransformUtil.beanToMapReturnString(
                UnifiedOrderDTO.builder()
                        .appid(appId)
                        .attach(GsonUtil.GSON.toJson(attach))
                        .body(payTitle)
                        .mch_id(mchId)
                        .nonce_str(uuid)
                        .notify_url(notifyUrl)
                        .out_trade_no(uuid)
                        .sign_type("MD5")
                        .spbill_create_ip(serverIp)
                        .total_fee(String.valueOf(pa.multiply(new BigDecimal(100)).intValue()))
                        .trade_type("JSAPI")
                        .openid(openId)
                        .build());
    }

    public String callback(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Map<String, String> params = RequestUtil.requestToXml(httpServletRequest);
        log.info("======购买会员-微信异步回调 callback -> 所有参数 params: " + params);

        String result = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";//返回给微信的数据
        if ("SUCCESS".equalsIgnoreCase(params.get("return_code")) && "SUCCESS".equalsIgnoreCase(params.get("result_code"))) {
            Gson gson = new Gson();
            PassBackP passBackP = gson.fromJson(params.get("attach"), PassBackP.class);

            WXPay wxPay = new WXPay(MyWxPayConfigImpl.builder().key(pgAppInfo.getPrivateKey()).build());

            boolean signVerified = wxPay.isPayResultNotifySignatureValid(params);
            log.info("======购买会员-微信异步回调 parkingWxCallback -> 验签结果 signVerified: " + signVerified);

            if (signVerified) {
                String totalFee = String.valueOf(MoneyUtil.format(Double.valueOf(params.get("total_fee")) / 100));
                Date gmtPayment = DateUtil.stringToDate(params.get("time_end"), "yyyyMMddHHmmss");
                callbackHandler(passBackP.getOrderId(), gmtPayment, totalFee, params.get("transaction_id"), params.get("out_trade_no"));
                result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            }
        }

        log.info("======购买会员-微信异步回调 parkingWxCallback -> 返回给支付宝的结果字符串 result: " + result);
        return result;
    }

    public void callbackHandler(Integer orderId, Date gmtPayment, String totalFee, String transaction_id, String out_trade_no) {
        VipOrderDO vipOrderDO = vipOrderDOMapper.selectByPrimaryKey(orderId);
        if (!Objects.isNull(vipOrderDO)) {
            VipOrderDO update = VipOrderDO.builder()
                    .id(vipOrderDO.getId())
                    .updateTime(DateUtil.now())
                    .realAmount(new BigDecimal(totalFee))
                    .status("PAID")
                    .payTime(gmtPayment)
                    .build();
            vipOrderDOMapper.updateByPrimaryKeySelective(update);
            PaymentDO paymentDO = paymentDOMapper.selectByPrimaryKey(vipOrderDO.getPaymentId());
            if (!Objects.isNull(paymentDO)) {
                paymentDOMapper.updateByPrimaryKeySelective(PaymentDO.builder()
                        .id(paymentDO.getId())
                        .updateTime(DateUtil.now())
                        .tradeNo(transaction_id)
                        .status("PAID")
                        .payTime(gmtPayment)
                        .build());
            }
            VipConfigDO vipConfigDO = vipConfigDOMapper.selectByPrimaryKey(vipOrderDO.getBuyType().intValue());
            if (!Objects.isNull(vipConfigDO)) {
                CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(vipOrderDO.getUserId());
                if (!Objects.isNull(carUserDO)) {
                    CarUserDO updateUser = CarUserDO.builder().id(carUserDO.getId()).updateTime(DateUtil.now())
                            .vipStartTime(DateUtil.now())
                            .build();
                    if (vipConfigDO.getIsLimit()) {
                        updateUser.setHasTry(Boolean.TRUE);
                    }
                    Calendar calendar = Calendar.getInstance();
                    String time = vipConfigDO.getDays().substring(0, vipConfigDO.getDays().length() - 1);
                    if (vipConfigDO.getDays().contains("d")) {
                        calendar.add(Calendar.DATE, Integer.parseInt(time));
                    } else if (vipConfigDO.getDays().contains("m")) {
                        calendar.add(Calendar.MONTH, Integer.parseInt(time));
                    } else {
                        calendar.add(Calendar.YEAR, Integer.parseInt(time));
                    }
                    updateUser.setVipEndTime(calendar.getTime());
                    carUserDOMapper.updateByPrimaryKeySelective(updateUser);
                }
                vipRecordDOMapper.insertSelective(VipRecordDO.builder()
                        .createTime(DateUtil.now())
                        .updateTime(DateUtil.now())
                        .userId(vipOrderDO.getUserId())
                        .days(vipConfigDO.getDays())
                        .price(vipConfigDO.getPrice())
                        .type((byte) 0)
                        .build());
            }
        }
    }

    public List<String> payUserList() {
        List<VipOrderDO> vipOrderDOS = vipOrderDOMapper.selectByStatus("PAID");
        if (ObjectUtils.isEmpty(vipOrderDOS)) {
            return Lists.newArrayList();
        }
        List<CarUserDO> userDOS = carUserDOMapper.selectBatchByPrimaryKey(vipOrderDOS.stream().map(VipOrderDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(userDOS) ? Maps.newHashMap() :
                userDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        List<VipConfigDO> configDOS = vipConfigDOMapper.selectAll();
        Map<Integer, VipConfigDO> map = configDOS.stream().collect(Collectors.toMap(VipConfigDO::getId, row -> row));
        return vipOrderDOS.stream().map(row -> {
            CarUserDO carUserDO = userMap.get(row.getUserId());
            String mobile = Objects.isNull(carUserDO) ? "" : carUserDO.getPhone();
            VipConfigDO configDO = map.get(row.getBuyType().intValue());
            return "用户" + hidePhone(mobile) + " " + DateUtil.dealTime(row.getUpdateTime()) + "购买了" + configDO.getDescription();
        }).collect(Collectors.toList());
    }

    /**
     * 手机号隐藏 例 ：158*****5566
     *
     * @param phone
     * @return
     */
    public String hidePhone(String phone) {
        if (phone == null || phone.length() < 3) {
            return "";
        }
        return hideStr(3, 4, 4, phone);
    }

    /**
     * 隐藏字符的显示
     *
     * @param preNum 字符前端要显示的字符数
     * @param sufNum 字符后端要显示的字符数
     * @param str
     * @return
     */
    public String hideStr(int preNum, int sufNum, int hideNum, String str) {
        if (str == null) {
            return str;
        }
        int len = str.length();
        if (preNum + sufNum > len) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if (sufNum > 0) {
            sb.append(str.substring(0, preNum)).append(creatHideChar(hideNum)).append(str.substring(len - sufNum));
        } else {
            sb.append(str.substring(0, preNum)).append(creatHideChar(hideNum));
        }
        return sb.toString();
    }

    /**
     * 生成*字符窜
     *
     * @param num
     * @return
     */
    public String creatHideChar(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append("*");
            num--;
        }
        return sb.toString();
    }

    private String getDays(String days) {
        return days.replaceAll("d", "天").replaceAll("m", "个月").replaceAll("y", "年");
    }
}
