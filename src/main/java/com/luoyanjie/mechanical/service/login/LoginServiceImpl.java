package com.luoyanjie.mechanical.service.login;

import com.luoyanjie.mechanical.bean.common.Constant;
import com.luoyanjie.mechanical.bean.dto.login.AdminLoginDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author luoyanjie
 * @date 2019-08-19 19:35
 * Coding happily every day!
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserService userService;

    @Override
    public AdminLoginDTO login(String account, String pwdByMd5) {
        if (StringUtils.isEmpty(account)) throw new RuntimeException("账户不能为空");
        if (StringUtils.isEmpty(pwdByMd5)) throw new RuntimeException("密码不能为空");

        UserDO userDO = userDOMapper.selectByPrimaryKey(Constant.ADMIN_USER_ID);
        if (userDO == null) throw new RuntimeException("系统用户不存在了");

        if (!Objects.equals(userDO.getAccount(), account)) throw new RuntimeException("该账户不能登录");
        if (!Objects.equals(userDO.getPassword(), pwdByMd5)) throw new RuntimeException("密码不正确");

        String token = userDO.getToken();
        if (StringUtils.isEmpty(userDO.getToken())) {
            token = TokenUtil.getToken(userDO.getAccount());
        }

        return AdminLoginDTO.builder()
                .id(userDO.getId())
                .token(token)
                .userDTO(userService.getUser(userDO))
                .build();
    }

    @Override
    public Boolean modifyPwdByAccount(String oldPwd, String newPwd, String account) {
        if (StringUtils.isEmpty(oldPwd)) throw new RuntimeException("原始密码不能为空");
        if (StringUtils.isEmpty(newPwd)) throw new RuntimeException("新密码不能为空");
        if (StringUtils.isEmpty(account)) throw new RuntimeException("账号不能为空");

        UserDO userDO = userDOMapper.selectByAccount(account);
        if (userDO == null) throw new RuntimeException("用户不存在了");

        if (!Objects.equals(userDO.getPassword(), oldPwd)) throw new RuntimeException("原密码不正确");

        modifyPwd(userDO.getId(), newPwd);

        return true;
    }

    @Override
    public Boolean modifyPwdById(String oldPwd, String newPwd, Integer id) {
        if (StringUtils.isEmpty(oldPwd)) throw new RuntimeException("原始密码不能为空");
        if (StringUtils.isEmpty(newPwd)) throw new RuntimeException("新密码不能为空");
        if (id == null) throw new RuntimeException("ID不能为空");

        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) throw new RuntimeException("用户不存在了");

        if (!Objects.equals(userDO.getPassword(), oldPwd)) throw new RuntimeException("原密码不正确");

        modifyPwd(userDO.getId(), newPwd);

        return true;
    }

    private void modifyPwd(Integer id, String newPwd) {
        userDOMapper.updateByPrimaryKeySelective(
                UserDO.builder()
                        .id(id)
                        .password(newPwd)
                        .build()
        );
    }
}
