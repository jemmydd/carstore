package com.luoyanjie.mechanical.bean.dto.login;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-19 19:37
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDTO {
    private Integer id;

    private UserDTO userDTO;

    private String token;
}
