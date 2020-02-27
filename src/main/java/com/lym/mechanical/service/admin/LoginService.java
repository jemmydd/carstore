package com.lym.mechanical.service.admin;

import com.lym.mechanical.bean.dto.admin.AdminLoginDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.param.admin.AdminLoginParam;
import com.lym.mechanical.bean.param.admin.AdminPasswordParam;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author liyimin
 * @create 2020-02-14 16:18
 * Good good code, day day up!
 **/
@Service
public class LoginService {

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    public AdminLoginDTO login(AdminLoginParam param) {
        CarUserDO carUserDO = carUserDOMapper.selectByName(param.getAccount());
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (!StringUtils.isEmpty(carUserDO.getOpenid()) && !Objects.equals(param.getPassword(), carUserDO.getOpenid())) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (StringUtils.isEmpty(carUserDO.getOpenid())) {
            carUserDOMapper.updateByPrimaryKeySelective(CarUserDO.builder().id(carUserDO.getId()).openid(param.getPassword()).build());
        }
        return AdminLoginDTO.builder()
                .avatar(carUserDO.getHeadPortrait())
                .name(carUserDO.getNickName())
                .token(carUserDO.getSessionKey())
                .userId(carUserDO.getId())
                .build();
    }

    public AdminLoginDTO modifyPwd(AdminPasswordParam param) {
        if (!Objects.equals(param.getNewPassword(), param.getNewPasswordAgain())) {
            throw new RuntimeException("两次密码不同");
        }
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(param.getUserId());
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        if (!Objects.equals(param.getOldPassword(), carUserDO.getOpenid())) {
            throw new RuntimeException("旧密码错误");
        }
        carUserDOMapper.updateByPrimaryKeySelective(CarUserDO.builder().id(param.getUserId()).openid(param.getNewPassword()).build());
        return AdminLoginDTO.builder()
                .avatar(carUserDO.getHeadPortrait())
                .name(carUserDO.getNickName())
                .token(carUserDO.getSessionKey())
                .userId(carUserDO.getId())
                .build();
    }
}
