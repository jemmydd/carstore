package com.luoyanjie.mechanical.service.wxPg;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.dto.wxPg.WxPgAuthDTO;
import com.luoyanjie.mechanical.bean.dto.wxPg.WxUserDTO;
import com.luoyanjie.mechanical.bean.dto.wxPg.WxUserPhoneDTO;
import com.luoyanjie.mechanical.bean.param.wxPg.QrParam;
import com.luoyanjie.mechanical.bean.param.wxPg.TemplateSendParam;

import java.io.IOException;

/**
 * @author luoyanjie
 * @date 2019-07-30 15:43
 * Coding happily every day!
 */
public interface WxPgService {
    UserDTO auth(String code);

    WxPgAuthDTO auth(String appId, String appSecret, String code);

    WxUserDTO userInfo(String accessToken, String openId);

    WxUserPhoneDTO getWxUserPhone(String code, String encryptedData, String iv);

    String getAccessToken() throws IOException;

    String getQr(QrParam param);

    String templateSend(TemplateSendParam param) throws IOException;
}
