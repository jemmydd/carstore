package com.lym.mechanical.service.wxPg;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.user.CarUserDTO;
import com.lym.mechanical.bean.dto.wxPg.AccessTokenDTO;
import com.lym.mechanical.bean.dto.wxPg.WxPgAuthDTO;
import com.lym.mechanical.bean.dto.wxPg.WxUserDTO;
import com.lym.mechanical.bean.dto.wxPg.WxUserPhoneDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.NameCardDO;
import com.lym.mechanical.bean.entity.WxAccessTokenDO;
import com.lym.mechanical.bean.entity.WxQrDO;
import com.lym.mechanical.bean.enumBean.WxQrEnum;
import com.lym.mechanical.bean.param.wxPg.QrParam;
import com.lym.mechanical.bean.param.wxPg.UserInfo;
import com.lym.mechanical.bean.param.wxPg.WxLoginInfo;
import com.lym.mechanical.component.oss.OssService;
import com.lym.mechanical.component.oss.OssServiceImpl;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.NameCardDOMapper;
import com.lym.mechanical.dao.mapper.WxAccessTokenDOMapper;
import com.lym.mechanical.dao.mapper.WxQrDOMapper;
import com.lym.mechanical.sys.FileDomain;
import com.lym.mechanical.sys.PgAppInfo;
import com.lym.mechanical.util.DateUtil;
import com.lym.mechanical.util.GsonUtil;
import com.lym.mechanical.util.OkHttp3Util;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author liyimin
 * @date 2019-11-05 09:38:10
 * good good code, day day up!
 */
@Slf4j
@Service
public class WxPgService {
    private static final String GRANT_TYPE_AUTH = "authorization_code";

    private static final String WX_PG_URL_PREFIX = "https://api.weixin.qq.com";

    private static final String WX_AUTH_URL = "/sns/jscode2session";

    private static final String ACCESS_TOKEN = "/sns/oauth2/access_token";

    private static final String REFRESH_TOKEN = "/sns/oauth2/refresh_token";

    private static final String USER_INFO = "/sns/userinfo";

    private static final String PRE_API_REGISTER = "http://preapi.itianluo.cn/user_register_block";

    private static final String API_REGISTER = "http://api.itianluo.cn/user_register_block";

    private static final String PRE_API_INIT = "http://preapi.itianluo.cn/init_castatus_block";

    private static final String API_INIT = "http://api.itianluo.cn/init_castatus_block";

    private static final Long EXPIRES_IN = 7200000L;

    @Autowired
    private PgAppInfo pgAppInfo;

    @Autowired
    private WxAccessTokenDOMapper wxAccessTokenDOMapper;

    @Autowired
    private WxQrDOMapper wxQrDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private OssService ossService;

    @Autowired
    private FileDomain fileDomain;

    @Autowired
    private NameCardDOMapper nameCardDOMapper;

    public CarUserDO auth(WxLoginInfo info) {
        CarUserDO userDO;

        WxPgAuthDTO wxPgAuthDTO = auth(pgAppInfo.getAppId(), pgAppInfo.getAppSecret(), info.getCode());

        UserInfo userInfo = info.getUserInfo();
        if (wxPgAuthDTO == null) {
            throw new RuntimeException("微信授权失败");
        } else {
            userDO = carUserDOMapper.selectByOpenId(wxPgAuthDTO.getOpenid());
            if (userDO == null) {
                userDO = CarUserDO.builder()
                        .createTime(DateUtil.now())
                        .updateTime(DateUtil.now())
                        .isDeleted((byte) 0)
                        .headPortrait(userInfo.getAvatarUrl())
                        .nickName(userInfo.getNickName().length() > 50 ? userInfo.getNickName().substring(0, 50) : userInfo.getNickName())
                        .sex(userInfo.getGender())
                        .openid(wxPgAuthDTO.getOpenid())
                        .sessionKey(wxPgAuthDTO.getSession_key())
                        .build();

                carUserDOMapper.insertSelective(userDO);
            }
            NameCardDO nameCardDO = nameCardDOMapper.selectByUserId(userDO.getId());
            userDO.setCardId(Objects.isNull(nameCardDO) ? null : nameCardDO.getId());
        }

        return userDO;
    }

    public WxPgAuthDTO auth(String appId, String appSecret, String code) {
        String url = String.format(WX_PG_URL_PREFIX + WX_AUTH_URL + "?appid=%s&secret=%s&js_code=%s&grant_type=%s", appId, appSecret, code, GRANT_TYPE_AUTH);

        String responseContent;
        try {
            responseContent = OkHttp3Util.simpleGet(url);
        } catch (IOException e) {
            throw new RuntimeException("[ " + WX_AUTH_URL + " ]调用失败");
        }

        if (StringUtils.isEmpty(responseContent)) {
            throw new RuntimeException("[ " + WX_AUTH_URL + " ]调用失败，没解析到响应");
        } else {
            WxPgAuthDTO wxPgAuthDTO = GsonUtil.GSON.fromJson(responseContent, WxPgAuthDTO.class);

            if (wxApiResponseError(wxPgAuthDTO.getErrcode())) {
                throw new RuntimeException(wxPgAuthDTO.getErrmsg());
            }

            return wxPgAuthDTO;
        }
    }

    public WxUserDTO userInfo(String accessToken, String openId) {
        String url = String.format(WX_PG_URL_PREFIX + USER_INFO + "access_token=%s&openid=%s", accessToken, openId);

        String responseContent;
        try {
            responseContent = OkHttp3Util.simpleGet(url);
        } catch (IOException e) {
            throw new RuntimeException("[ " + USER_INFO + " ]调用失败");
        }
        if (StringUtils.isEmpty(responseContent)) {
            throw new RuntimeException("[ " + USER_INFO + " ]调用失败，没解析到响应");
        } else {
            WxUserDTO wxUserDTO = GsonUtil.GSON.fromJson(responseContent, WxUserDTO.class);

            if (wxApiResponseError(wxUserDTO.getErrcode())) {
                throw new RuntimeException(wxUserDTO.getErrmsg());
            }

            return wxUserDTO;
        }
    }

    public WxUserPhoneDTO getWxUserPhone(Integer userId, String encryptedData, String iv) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);

        if (carUserDO == null) {
            throw new RuntimeException("用户不存在");
        } else {
            try {
                WxUserPhoneDTO phoneDTO = GsonUtil.GSON.fromJson(decrypt(carUserDO.getSessionKey(), iv, encryptedData), WxUserPhoneDTO.class);
                CarUserDO update = CarUserDO.builder().id(userId).updateTime(DateUtil.now()).phone(phoneDTO.getPhoneNumber()).build();
                carUserDOMapper.updateByPrimaryKeySelective(update);
                return phoneDTO;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("获取用户手机号失败");
            }
        }
    }

    public String getAccessToken() throws IOException {
        WxAccessTokenDO wxAccessTokenDO = wxAccessTokenDOMapper.selectByPrimaryKey(2);

        if (wxAccessTokenDO == null) {
            throw new RuntimeException("小程序配置错误");
        } else {
            log.info("获取小程序accessToken");
            log.info("" + (new Date().getTime()));
            log.info("" + (wxAccessTokenDO.getUpdateTime().getTime()));
            log.info("" + (new Date().getTime() - wxAccessTokenDO.getUpdateTime().getTime()));
            log.info("" + (EXPIRES_IN - 1));
            log.info("" + (StringUtils.isEmpty(wxAccessTokenDO.getAccessToken())
                    || (new Date().getTime() - wxAccessTokenDO.getUpdateTime().getTime()) >= (EXPIRES_IN - 1)));
            if (StringUtils.isEmpty(wxAccessTokenDO.getAccessToken())
                    || (new Date().getTime() - wxAccessTokenDO.getUpdateTime().getTime()) >= (EXPIRES_IN - 1)) {
                String newData = getAccessTokenFromWx();
                if (newData == null) throw new RuntimeException("小程序获取accessToken失败");

                wxAccessTokenDO.setAccessToken(newData);

                wxAccessTokenDOMapper.updateByPrimaryKeySelective(
                        WxAccessTokenDO.builder()
                                .id(2)
                                .accessToken(newData)
                                .build()
                );
            }
        }

        log.info(GsonUtil.GSON.toJson(wxAccessTokenDO));
        return wxAccessTokenDO.getAccessToken();
    }

    public String getQr(QrParam param) {
        String result;

        String paramJson = SecureUtil.md5(GsonUtil.GSON.toJson(param));
        WxQrDO wxQrDO = wxQrDOMapper.selectByMd5Param(paramJson, WxQrEnum.NAME_CARD.name());

        if (wxQrDO == null || StringUtils.isEmpty(wxQrDO.getUrl())) {
            InputStream inputStream = getQrResponseBody(param);

            int index = 1;
            while (inputStream == null && index < 10) {
                log.info("dodododo" + index);
                inputStream = getQrResponseBody(param);
                index++;
            }

            String key = "qr/" + OssServiceImpl.key() + ".png";
            ossService.upload(key, inputStream);

            result = fileDomain.addDomain(key);

            if (wxQrDO == null) {
                wxQrDOMapper.insertSelective(
                        WxQrDO.builder()
                                .md5Param(paramJson)
                                .type(WxQrEnum.NAME_CARD.name())
                                .url(result)
                                .build()
                );
            } else {
                wxQrDOMapper.updateByPrimaryKeySelective(
                        WxQrDO.builder()
                                .id(wxQrDO.getId())
                                .url(result)
                                .build()
                );
            }
        } else {
            result = wxQrDO.getUrl();
        }

        return result;
    }

    private InputStream getQrResponseBody(QrParam param) {
        Response response = null;
        try {
            //RequestBody body = RequestBody.create(MediaType.parse("application/json"), GsonUtil.GSON.toJson(param));

            Map<String, String> params = Maps.newHashMap();
            if (!StringUtils.isEmpty(param.getAuto_color())) params.put("auto_color", param.getAuto_color());
            if (!StringUtils.isEmpty(param.getIs_hyaline())) params.put("is_hyaline", param.getIs_hyaline());
            if (!StringUtils.isEmpty(param.getLine_color())) params.put("line_color", param.getLine_color());
            if (!StringUtils.isEmpty(param.getPage())) params.put("page", param.getPage());
            if (!StringUtils.isEmpty(param.getScene())) params.put("scene", param.getScene());
            if (!StringUtils.isEmpty(param.getWidth())) params.put("width", param.getWidth());
            String js = GsonUtil.GSON.toJson(params);
            log.info(js);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), js);

            Request request = new Request.Builder()
                    .url(String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s", getAccessToken()))
                    .post(body)
                    .addHeader("Content-Type", "image/png")
                    .build();

            response = new OkHttpClient.Builder().build().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("请求QR异常");
        }

        return response == null || response.body() == null ? null : response.body().byteStream();
    }

    public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName) {
        int stateInt = 1;
        if (instreams != null) {
            try {
                File file = new File(imgName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }

    private static Boolean wxApiResponseError(Long errcode) {
        return errcode != null && !Objects.equals(errcode, 0L);
    }

    private static String decrypt(String keyStr, String ivStr, String encDataStr) throws Exception {

        byte[] encData = Base64.decode(encDataStr);
        byte[] iv = Base64.decode(ivStr);
        byte[] key = Base64.decode(keyStr);

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return new String(cipher.doFinal(encData), StandardCharsets.UTF_8);
    }

    private String getAccessTokenFromWx() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //获取微信的accessToken
        Request request = new Request.Builder().url(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                pgAppInfo.getAppId(), pgAppInfo.getAppSecret())).build();

        Response response = okHttpClient.newCall(request).execute();

        ResponseBody responseBody = response.body();
        if (responseBody == null) return null;

        String as = responseBody.string();
        log.info(as);

        AccessTokenDTO accessTokenDTO = GsonUtil.GSON.fromJson(as, AccessTokenDTO.class);

        return accessTokenDTO.getAccess_token();
    }
}
