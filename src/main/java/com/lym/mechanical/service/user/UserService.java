package com.lym.mechanical.service.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lym.mechanical.bean.common.DefaultHandleConstant;
import com.lym.mechanical.bean.dto.user.UserDTO;
import com.lym.mechanical.bean.entity.UserDO;
import com.lym.mechanical.bean.enumBean.SexEnum;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.DiscussDOMapper;
import com.lym.mechanical.dao.mapper.UserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private DiscussDOMapper discussDOMapper;

    public Map<Integer, UserDO> getUserMap(List<Integer> userIds) {
        return userDOMapper.selectBatchByPrimaryKey(userIds).stream().collect(Collectors.toMap(UserDO::getId, u -> u));
    }

    public UserDTO getUser(Integer userId) {
        return getUser(userDOMapper.selectByPrimaryKey(userId));
    }

    public UserDTO getUser(UserDO userDO) {
        return getUser(userDO, true);
    }

    public String getUserLocation(UserDO userDO) {
        return getUserLocation(userDO.getProvinceName(), userDO.getCityName(), userDO.getAreaName());
    }

    public String getUserLocation(String pName, String cName, String aName) {
        return (StringUtils.isEmpty(pName) ? "" : pName)
                + "·"
                + (StringUtils.isEmpty(cName) ? "" : cName)
                + "·"
                + (StringUtils.isEmpty(aName) ? "" : aName);
    }

//    public PageData<UserDTO> search(Integer pageNum, Integer pageSize, String provinceCode, String cityCode, String areaCode, String phone) {
//        PageData.checkPageParam(pageNum, pageSize);
//
//        PageHelper.startPage(pageNum, pageSize);
//        Page<UserDO> pd = (Page<UserDO>) userDOMapper.searchForAdmin(provinceCode, cityCode, areaCode, phone);
//
//        List<UserDO> data = pd.getResult();
//        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);
//
//        return PageData.data(pd, data.stream().map(row -> getUser(row, false)).collect(Collectors.toList()));
//    }

//    public List<UserDTO> allNotDelete() {
//        return userDOMapper.selectAllNotDelete().stream().map(row -> getUser(row, false)).collect(Collectors.toList());
//    }
//
//    public List<UserSimpleDTO> allNotDeleteSimple() {
//        return userDOMapper.selectAllNotDelete()
//                .stream()
//                .map(row -> UserSimpleDTO.builder().id(row.getId()).nickName(StringUtils.isEmpty(row.getNickName()) ? "" : row.getNickName()).build())
//                .collect(Collectors.toList());
//    }

    public Boolean delete(Integer userId) {
        if (userId == null) throw new RuntimeException("用户ID不能为空");

        userDOMapper.updateByPrimaryKeySelective(
                UserDO.builder()
                        .id(userId)
                        .isDeleted((byte) 1)
                        .build()
        );

        return true;
    }

    public Boolean recover(Integer userId) {
        if (userId == null) throw new RuntimeException("用户ID不能为空");

        userDOMapper.updateByPrimaryKeySelective(
                UserDO.builder()
                        .id(userId)
                        .isDeleted((byte) 0)
                        .build()
        );

        return true;
    }

    public Boolean bindPhone(Integer userId, String phone) {
        if (userId == null) throw new RuntimeException("用户ID不能为空");
        if (StringUtils.isEmpty(phone)) throw new RuntimeException("手机号不能为空");

        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null) throw new RuntimeException("用户不存在");

        if (!StringUtils.isEmpty(userDO.getPhone())) throw new RuntimeException("已经绑定了手机号，请先解绑");

        userDOMapper.updateByPrimaryKeySelective(
                UserDO.builder()
                        .id(userId)
                        .phone(phone)
                        .build()
        );

        return true;
    }

//    public Boolean unBindPhone(Integer userId) {
//        if (userId == null) throw new RuntimeException("用户ID不能为空");
//
//        userDOMapper.unBindPhone(userId);
//
//        return true;
//    }

    private UserDTO getUser(UserDO userDO, Boolean hasDiscuss) {
        UserDTO userDTO;
        if (userDO == null) {
            userDTO = UserDTO.builder()
                    .areaCode("")
                    .areaName("")
                    .backgroundImage("")
                    .cityCode("")
                    .cityName("")
                    .collectionCount(-1)
                    .createTime("")
                    .fansCount(-1)
                    .followCount(-1)
                    .headPortrait("")
                    .id(-1)
                    .inviteCount(0)
                    .isDeleted((byte) 1)
                    .location("")
                    .name("")
                    .newBind(false)
                    .nickName("")
                    .phone("")
                    .provinceCode("")
                    .provinceName("")
                    .publishCount(0)
                    .sex("")
                    .signature("")
                    .unreadCommentCount(0)
                    .purchaseInformationCount(0)
                    .userIdentity("")
                    .viewPhoneCount(0)
                    .build();
        } else {
            userDTO = UserDTO.builder()
                    .areaCode(userDO.getAreaCode())
                    .areaName(userDO.getAreaName())
                    .backgroundImage(userDO.getBackgroundImage())
                    .cityCode(userDO.getAreaCode())
                    .cityName(userDO.getNickName())
                    .collectionCount(userDO.getCollectionCount())
                    .createTime(userDO.getCreateTime() == null ? "" : DateFormatUtils.format(userDO.getCreateTime(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()))
                    .fansCount(userDO.getFansCount())
                    .followCount(userDO.getFollowCount())
                    .headPortrait(getHeadPortrait(userDO.getHeadPortrait()))
                    .id(userDO.getId())
                    .inviteCount(userDO.getInviteCount() == null ? 0 : userDO.getInviteCount())
                    .isDeleted(userDO.getIsDeleted())
                    .location(getUserLocation(userDO))
                    .name(userDO.getName())
                    .newBind(userDO.getNewBind() != null && (Objects.equals(userDO.getNewBind(), (byte) 1)))
                    .nickName(getNickName(userDO.getNickName()))
                    .phone(getPhone(userDO.getPhone()))
                    .provinceCode(userDO.getProvinceCode())
                    .provinceName(userDO.getProvinceName())
                    .publishCount(userDO.getPublishCount())
                    .purchaseInformationCount(userDO.getPurchaseInformationCount() == null ? 0 : userDO.getPurchaseInformationCount())
                    .sex(SexEnum.getTextByCode(userDO.getSex()))
                    .signature(getSignature(userDO.getSignature()))
                    .unreadCommentCount(hasDiscuss ? discussDOMapper.selectUnreadByPublishUser(userDO.getId()) : null)
                    .userIdentity(StringUtils.isEmpty(userDO.getUserIdentity()) ? "" : userDO.getUserIdentity())
                    .viewPhoneCount(userDO.getViewPhoneCount())
                    .build();
        }

        return userDTO;
    }

    public static String getHeadPortrait(String headPortrait) {
        return StringUtils.isEmpty(headPortrait) ? DefaultHandleConstant.USER_HEAD_PORTRAIT : (headPortrait);
    }

    public static String getNickName(String nickName) {
        return StringUtils.isEmpty(nickName) ? DefaultHandleConstant.USER_NICKNAME : nickName;
    }

    private static String getPhone(String phone) {
        return StringUtils.isEmpty(phone) ? DefaultHandleConstant.USER_PHONE : phone;
    }

    private static String getSignature(String signature) {
        return StringUtils.isEmpty(signature) ? DefaultHandleConstant.USER_SIGNATURE : signature;
    }
}
