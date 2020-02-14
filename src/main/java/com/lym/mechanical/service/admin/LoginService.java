package com.lym.mechanical.service.admin;

import com.lym.mechanical.bean.dto.admin.AdminLoginDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.param.admin.AdminLoginParam;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
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
        if (!Objects.equals(param.getPassword(), carUserDO.getOpenid())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return AdminLoginDTO.builder()
                .avatar(carUserDO.getHeadPortrait())
                .name(carUserDO.getNickName())
                .token(carUserDO.getSessionKey())
                .userId(carUserDO.getId())
                .build();
    }
}
