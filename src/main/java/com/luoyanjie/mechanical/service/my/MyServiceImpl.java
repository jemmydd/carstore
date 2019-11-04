package com.luoyanjie.mechanical.service.my;

import com.google.common.base.Verify;
import com.luoyanjie.mechanical.bean.dto.discuss.DiscussDTO;
import com.luoyanjie.mechanical.bean.dto.invite.InviteDTO;
import com.luoyanjie.mechanical.bean.dto.my.FansDTO;
import com.luoyanjie.mechanical.bean.dto.my.PersonalHomePageDTO;
import com.luoyanjie.mechanical.bean.dto.my.PublishInHpDTO;
import com.luoyanjie.mechanical.bean.dto.my.v2.PersonalHomePageV2DTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.dto.purchaseInformation.PurchaseInformationDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.bean.enumBean.PublishCallSceneEnum;
import com.luoyanjie.mechanical.bean.param.my.ModifyPersonalHomePageBkParam;
import com.luoyanjie.mechanical.bean.param.my.PersonalDataParam;
import com.luoyanjie.mechanical.bean.param.publish.PublishParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.RollIdPageData;
import com.luoyanjie.mechanical.dao.mapper.FollowDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.invite.InviteService;
import com.luoyanjie.mechanical.service.purchaseInformation.PurchaseInformationService;
import com.luoyanjie.mechanical.service.collection.CollectionService;
import com.luoyanjie.mechanical.service.discuss.DiscussService;
import com.luoyanjie.mechanical.service.follow.FollowService;
import com.luoyanjie.mechanical.service.publish.PublishService;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.sys.ExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-07-30 09:07
 * Coding happily every day!
 */
@Slf4j
@Service
public class MyServiceImpl implements MyService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private FollowDOMapper followDOMapper;

    @Autowired
    private PublishService publishService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussService discussService;

    @Autowired
    private FollowService followService;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private PurchaseInformationService purchaseInformationService;

    @Override
    public UserDTO getOverview(Integer userId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));

        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null) throw new RuntimeException("用户不存在");

        return userService.getUser(userDO);
    }

    @Override
    public UserDTO getPersonalData(Integer userId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));

        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null) throw new RuntimeException("用户不存在");

        return userService.getUser(userDO);
    }

    @Transactional
    @Override
    public Boolean modifyPersonalData(PersonalDataParam param) {
        if (param.getUserId() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));

        UserDO userDO = userDOMapper.selectByPrimaryKey(param.getUserId());
        if (userDO == null) throw new RuntimeException("用户不存在");

        boolean result;
        try {
            UserDO un = UserDO.builder()
                    .id(userDO.getId())
                    .areaCode(StringUtils.isEmpty(param.getAreaCode()) ? null : param.getAreaCode())
                    .areaName(StringUtils.isEmpty(param.getAreaName()) ? null : param.getAreaName())
                    .cityCode(StringUtils.isEmpty(param.getCityCode()) ? null : param.getCityCode())
                    .cityName(StringUtils.isEmpty(param.getCityName()) ? null : param.getCityName())
                    .headPortrait(StringUtils.isEmpty(param.getHeadPortrait()) ? null : param.getHeadPortrait())
                    .newBind((byte) 0)
                    .nickName(StringUtils.isEmpty(param.getNickName()) ? null : param.getNickName())
                    .provinceCode(StringUtils.isEmpty(param.getProvinceCode()) ? null : param.getProvinceCode())
                    .provinceName(StringUtils.isEmpty(param.getProvinceName()) ? null : param.getProvinceName())
                    .signature(StringUtils.isEmpty(param.getSignature()) ? null : param.getSignature())
                    .password(userDO.getPassword()) //防止全控报错
                    .build();
            userDOMapper.updateByPrimaryKeySelective(un);
            result = true;
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public PersonalHomePageDTO getPersonalHomePage(Integer userId, Integer whoId, Integer baseSize) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (whoId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "主页者ID"));
        if (baseSize == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "最底层的数据也大小"));

        UserDO who = userDOMapper.selectByPrimaryKey(whoId);
        if (who == null) throw new RuntimeException("主页不存在");

        boolean followed;
        boolean whoIsRequestUser = Objects.equals(userId, whoId);
        if (whoIsRequestUser) {
            followed = true;
        } else {
            List<Integer> toIds = followDOMapper.selectToByFrom(userId);
            followed = toIds.contains(whoId);
        }

        return PersonalHomePageDTO.builder()
                .followed(followed)
                .publishInHp(publishService.getForHomePage(whoId, baseSize, null))
                .whoInfo(userService.getUser(who))
                .whoIsRequestUser(whoIsRequestUser)
                .build();
    }

    @Override
    public RollIdPageData<PublishInHpDTO> getPersonalHomePagePublish(Integer userId, Integer whoId, Integer baseSize, Integer currentMinId) {
        return publishService.getForHomePage(whoId, baseSize, currentMinId);
    }

    @Override
    public Boolean modifyPersonalHomePageBk(ModifyPersonalHomePageBkParam param) {
        Verify.verify(param.getUserId() != null, ExceptionAdvice.NOT_EMPTY, "用户ID");
        Verify.verify(!ObjectUtils.isEmpty(param.getBackgroundImage()), ExceptionAdvice.NOT_EMPTY, "背景图片");

        boolean result;
        try {
            userDOMapper.updateByPrimaryKeySelective(
                    UserDO.builder()
                            .id(param.getUserId())
                            .backgroundImage(param.getBackgroundImage())
                            .build()
            );
            result = true;
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public PageData<PublishDTO> getMyPublishPageData(Integer pageNum, Integer pageSize, Integer userId, Byte downShelf) {
        return publishService.getPageData(PublishParam.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .publishCallSceneEnum(PublishCallSceneEnum.MY_PUBLISH)
                .userId(userId)
                .isDownShelf(downShelf)
                .build());
    }

    @Override
    public PageData<PublishDTO> getMyCollectionPageData(Integer pageNum, Integer pageSize, Integer userId) {
        return collectionService.getPageData(pageNum, pageSize, userId);
    }

    @Override
    public PageData<DiscussDTO> getMyDiscussPageData(Integer pageNum, Integer pageSize, Integer userId) {
        PageData<DiscussDTO> pageData = discussService.getPageDataWithOutRequest(pageNum, pageSize, userId);

        if (pageData != null && !ObjectUtils.isEmpty(pageData.getItems())) discussService.toReadBatch(pageData.getItems().stream().map(DiscussDTO::getId).distinct().collect(Collectors.toList()));

        return pageData;
    }

    @Override
    public PageData<FansDTO> getMyFansPageData(Integer pageNum, Integer pageSize, Integer userId) {
        return followService.getFansPageData(userId, pageNum, pageSize, userId);
    }

    @Override
    public PageData<FansDTO> getMyFollowPageData(Integer pageNum, Integer pageSize, Integer userId) {
        return followService.getFollowPageData(userId, pageNum, pageSize, userId);
    }

    @Override
    public PageData<PurchaseInformationDTO> myPurchaseInformationPageData(Integer pageNum, Integer pageSize, Integer userId, Byte shelfStatus) {
        return purchaseInformationService.search(
                pageNum, pageSize, null, null, null, null, null, null, null, shelfStatus, userId, null
        );
    }

    @Override
    public PageData<InviteDTO> myInvitePageData(Integer pageNum, Integer pageSize, Integer userId) {
        return inviteService.pageData(pageNum, pageSize, userId);
    }

    @Override
    public PersonalHomePageV2DTO getPersonalHomePageV2(Integer userId, Integer whoId, Integer pageSize) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (whoId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "主页者ID"));
        if (pageSize == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "页大小"));

        UserDO who = userDOMapper.selectByPrimaryKey(whoId);
        if (who == null) throw new RuntimeException("主页者不存在");

        boolean followed;
        boolean whoIsRequestUser = Objects.equals(userId, whoId);
        if (whoIsRequestUser) {
            followed = true;
        } else {
            List<Integer> toIds = followDOMapper.selectToByFrom(userId);
            followed = toIds.contains(whoId);
        }

        return PersonalHomePageV2DTO.builder()
                .followed(followed)
                .publishInHp(
                        publishService.getPageData(PublishParam.builder()
                                .pageNum(1)
                                .pageSize(pageSize)
                                .publishCallSceneEnum(PublishCallSceneEnum.PERSON_HOME)
                                .publishId(whoId)
                                .userId(userId)
                                .build())
                )
                .whoInfo(userService.getUser(who))
                .whoIsRequestUser(whoIsRequestUser)
                .build();
    }

    @Override
    public PageData<PublishDTO> getPersonalHomePagePublishV2(Integer userId, Integer whoId, Integer pageSize, Integer pageNum) {
        return publishService.getPageData(PublishParam.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .publishCallSceneEnum(PublishCallSceneEnum.PERSON_HOME)
                .publishId(whoId)
                .userId(userId)
                .build());
    }
}
