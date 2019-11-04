package com.luoyanjie.mechanical.service.login;

import com.luoyanjie.mechanical.bean.dto.login.AdminLoginDTO;

/**
 * @author luoyanjie
 * @date 2019-08-19 19:35
 * Coding happily every day!
 */
public interface LoginService {
    AdminLoginDTO login(String account, String pwdByMd5);

    Boolean modifyPwdByAccount(String oldPwd, String newPwd, String account);

    Boolean modifyPwdById(String oldPwd, String newPwd, Integer id);
}
