package com.luoyanjie.mechanical.service.user;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserSimpleDTO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.component.result.PageData;

import java.util.List;
import java.util.Map;

/**
 * @author luoyanjie
 * @date 2019-07-29 16:05
 * Coding happily every day!
 */
public interface UserService {
    Map<Integer, UserDO> getUserMap(List<Integer> userIds);

    UserDTO getUser(Integer userId);

    UserDTO getUser(UserDO userDO);

    String getUserLocation(UserDO userDO);

    String getUserLocation(String pName, String cName, String aName);

    PageData<UserDTO> search(Integer pageNum, Integer pageSize, String provinceCode, String cityCode, String areaCode, String phone);

    List<UserDTO> allNotDelete();

    List<UserSimpleDTO> allNotDeleteSimple();

    Boolean delete(Integer userId);

    Boolean recover(Integer userId);

    Boolean bindPhone(Integer userId, String phone);

    Boolean unBindPhone(Integer userId);
}
