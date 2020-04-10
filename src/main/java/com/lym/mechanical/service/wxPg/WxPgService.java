package com.lym.mechanical.service.wxPg;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.user.CarUserDTO;
import com.lym.mechanical.bean.dto.user.ProfileItem;
import com.lym.mechanical.bean.dto.user.TLSSigAPIv2;
import com.lym.mechanical.bean.dto.user.UserSetInfoDTO;
import com.lym.mechanical.bean.dto.wxPg.AccessTokenDTO;
import com.lym.mechanical.bean.dto.wxPg.WxPgAuthDTO;
import com.lym.mechanical.bean.dto.wxPg.WxUserDTO;
import com.lym.mechanical.bean.dto.wxPg.WxUserPhoneDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.NameCardDO;
import com.lym.mechanical.bean.entity.WxAccessTokenDO;
import com.lym.mechanical.bean.entity.WxQrDO;
import com.lym.mechanical.bean.enumBean.WxQrEnum;
import com.lym.mechanical.bean.param.wxPg.GetMobileParam;
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
import com.lym.mechanical.sys.TencentYunInfo;
import com.lym.mechanical.util.DateUtil;
import com.lym.mechanical.util.GsonUtil;
import com.lym.mechanical.util.OkHttp3Util;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;

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

    @Autowired
    private TencentYunInfo tencentYunInfo;

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
            } else {
                userDO.setHeadPortrait(userInfo.getAvatarUrl());
                userDO.setNickName(userInfo.getNickName().length() > 50 ? userInfo.getNickName().substring(0, 50) : userInfo.getNickName());
                userDO.setSex(userInfo.getGender());
                userDO.setSessionKey(wxPgAuthDTO.getSession_key());
                carUserDOMapper.updateByPrimaryKeySelective(CarUserDO.builder()
                        .id(userDO.getId())
                        .updateTime(DateUtil.now())
                        .headPortrait(userInfo.getAvatarUrl())
                        .nickName(userInfo.getNickName().length() > 50 ? userInfo.getNickName().substring(0, 50) : userInfo.getNickName())
                        .sex(userInfo.getGender())
                        .sessionKey(wxPgAuthDTO.getSession_key())
                        .build());
            }
            NameCardDO nameCardDO = nameCardDOMapper.selectByUserId(userDO.getId());
            userDO.setCardId(Objects.isNull(nameCardDO) ? null : nameCardDO.getId());
        }
        userDO.setIsVip(isVip(userDO.getVipStartTime(), userDO.getVipEndTime()));
        return userDO;
    }

    private Boolean isVip(Date vipStartTime, Date vipEndTime) {
        Date now = DateUtil.now();
        return !Objects.isNull(vipStartTime) && !Objects.isNull(vipEndTime) && now.compareTo(vipStartTime) >= 0 && now.compareTo(vipEndTime) <= 0;
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

    public WxUserPhoneDTO getWxUserPhone(GetMobileParam param) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(param.getUserId());

        if (carUserDO == null) {
            throw new RuntimeException("用户不存在");
        } else {
            try {
                WxPgAuthDTO wxPgAuthDTO = auth(pgAppInfo.getAppId(), pgAppInfo.getAppSecret(), param.getCode());
                WxUserPhoneDTO phoneDTO = GsonUtil.GSON.fromJson(decrypt(wxPgAuthDTO.getSession_key(), param.getIv(), param.getEncryptedData()), WxUserPhoneDTO.class);
                CarUserDO update = CarUserDO.builder().id(param.getUserId()).sessionKey(wxPgAuthDTO.getSession_key()).updateTime(DateUtil.now()).phone(phoneDTO.getPhoneNumber()).build();
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

        // 被加密的数据
        byte[] dataByte = Base64.decode(encDataStr);
        // 加密秘钥
        byte[] keyByte = Base64.decode(keyStr);
        // 偏移量
        byte[] ivByte = Base64.decode(ivStr);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            throw new RuntimeException("解密时遇到错误");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        decrypt("q6XVfjAbijONBHTtwNz38g==", "RpA6UyUbOvx7jxaIH3sGZQ==", "Nq1iI5eLvasm+JOMj9A77c7K3nYVLUcEP8mVDq1hi5iC4CQZCohkjBHlgxdp7iGONYdN7ft1gdacjrqwPkJ/ERwQQeg0ALdix2VqAL9VKnNfWjueBe2hYFFfu9JYlUXKN5F+KA9njq8eyJ0OiBqaNTLb005reynRIw1rOtMmdLyJjiKD43Mrgik76gqMCBEGV7EVd1UGC08qeGNYmAikQA==");
//        TLSSigAPIv2 api = new TLSSigAPIv2(1400000000, "5bd2850fff3ecb11d7c805251c51ee463a25727bddc2385f3fa8bfee1bb93b5e");
//        System.out.print(api.genSig("xiaojun", 180*86400));
        System.out.println(DateUtil.stringToDate("2020-03-17", "yyyy-MM-dd").getTime());
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

    public String userSig(Integer userId) throws Exception {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        String userSig;
        if (StringUtils.isEmpty(carUserDO.getUserSig()) || (DateUtil.now().getTime() - carUserDO.getSigCreateTime().getTime()) > 179 * 86400 * 1000L) {
            TLSSigAPIv2 api = new TLSSigAPIv2(tencentYunInfo.getSdkappid(), tencentYunInfo.getKey());
            userSig = api.genSig(userId.toString(), 180*86400);
            carUserDO.setUserSig(userSig);
            carUserDOMapper.updateByPrimaryKeySelective(CarUserDO.builder().id(carUserDO.getId()).userSig(userSig).sigCreateTime(DateUtil.now()).build());
        } else {
            userSig = carUserDO.getUserSig();
        }
        setUserInfo(carUserDO);
        return userSig;
    }

    public void setUserInfo(CarUserDO carUserDO) throws Exception {
        TLSSigAPIv2 api = new TLSSigAPIv2(tencentYunInfo.getSdkappid(), tencentYunInfo.getKey());
        String userSig = api.genSig(tencentYunInfo.getManager(), 180*86400);
        String url = String.format("https://console.tim.qq.com/v4/profile/portrait_set?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json",
                tencentYunInfo.getSdkappid(), tencentYunInfo.getManager(), userSig, random32Number());
        UserSetInfoDTO infoDTO = UserSetInfoDTO.builder()
                .From_Account(carUserDO.getId().toString())
                .ProfileItem(Lists.newArrayList(
                        ProfileItem.builder().Tag("Tag_Profile_IM_Nick").Value(carUserDO.getNickName()).build(),
                        ProfileItem.builder().Tag("Tag_Profile_IM_Image").Value(carUserDO.getHeadPortrait()).build()
                )).build();
        String js = GsonUtil.GSON.toJson(infoDTO);
        log.info(js);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), js);
        OkHttp3Util.simplePost(url, body);
    }

    private String random32Number() {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 32; i++){
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }
        return String.valueOf(sb);
    }
}
