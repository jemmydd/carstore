package com.lym.mechanical.service.card;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.common.DefaultHandleConstant;
import com.lym.mechanical.bean.dto.card.*;
import com.lym.mechanical.bean.dto.location.LocationDetailDTO;
import com.lym.mechanical.bean.dto.publish.PublishDTO;
import com.lym.mechanical.bean.dto.publish.PublishImageVideoDTO;
import com.lym.mechanical.bean.dto.user.UserDTO;
import com.lym.mechanical.bean.dto.wxPg.OperateDTO;
import com.lym.mechanical.bean.entity.*;
import com.lym.mechanical.bean.enumBean.OperateTypeEnum;
import com.lym.mechanical.bean.enumBean.PublishCallSceneEnum;
import com.lym.mechanical.bean.enumBean.PublishShelfStatusEnum;
import com.lym.mechanical.bean.enumBean.SexEnum;
import com.lym.mechanical.bean.param.card.ApplyCarStoreParam;
import com.lym.mechanical.bean.param.card.NameCardParam;
import com.lym.mechanical.dao.mapper.*;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname NameCardService
 * @Description
 * @Date 2019/11/6 17:51
 * @Created by jimy
 * good good code, day day up!
 */
@Service
public class NameCardService {

    @Autowired
    private NameCardDOMapper nameCardDOMapper;

    @Autowired
    private NameCardFriendDOMapper nameCardFriendDOMapper;

    @Autowired
    private NameCardImageVideoMapper nameCardImageVideoMapper;

    @Autowired
    private CarUserApplyDOMapper carUserApplyDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private MessageDOMapper messageDOMapper;

    @Autowired
    private BrandDOMapper brandDOMapper;

    @Autowired
    private DiscussDOMapper discussDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private PublishImageVideoDOMapper publishImageVideoDOMapper;

    @Autowired
    private CategoryDOMapper categoryDOMapper;

    @Autowired
    private NameCardLookRecordDOMapper nameCardLookRecordDOMapper;

    @Autowired
    private FriendCardRecordDOMapper friendCardRecordDOMapper;

    @Autowired
    private PublishLookRecordDOMapper publishLookRecordDOMapper;

    public MyNameCardDTO myNameCard(Integer userId) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        NameCardDO nameCardDO = nameCardDOMapper.selectByUserId(userId);
        List<CarUserApplyDO> carUserApplyDOS = carUserApplyDOMapper.selectByUserId(userId);
        String applyStatus = getApplyStatus(carUserApplyDOS);
        Boolean hasCard = !Objects.isNull(nameCardDO);
        List<RecentlyUserDTO> recentlyUsers = Lists.newArrayList();
        List<NameCardDTO> refereeCards = Lists.newArrayList();
        if (hasCard) {
            List<PublishLookRecordDO> publishLookRecordDOS = publishLookRecordDOMapper.selectByPublishUserId(userId);
            if (!ObjectUtils.isEmpty(publishLookRecordDOS)) {
                List<Integer> userIds = publishLookRecordDOS.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList());
                List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(userIds);
                Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                        carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
                List<Integer> publishIds = publishLookRecordDOS.stream().map(PublishLookRecordDO::getPublishId).distinct().collect(Collectors.toList());
                List<PublishDO> publishDOS = publishDOMapper.selectBatchByPrimaryKey(publishIds);
                Map<Integer, PublishDO> publishMap = ObjectUtils.isEmpty(publishDOS) ? Maps.newHashMap() :
                        publishDOS.stream().collect(Collectors.toMap(PublishDO::getId, row -> row));
                recentlyUsers = publishLookRecordDOS.stream().map(row -> {
                    CarUserDO userDO = userMap.get(row.getUserId());
                    PublishDO publishDO = publishMap.get(row.getPublishId());
                    return RecentlyUserDTO.builder()
                            .userId(row.getUserId())
                            .avatar(Objects.isNull(userDO) ? "" : userDO.getHeadPortrait())
                            .nickName(Objects.isNull(userDO) ? "" : userDO.getNickName())
                            .publishId(row.getPublishId())
                            .time(DateUtil.dealTime(row.getCreateTime()))
                            .publishName(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                            .build();
                }).collect(Collectors.toList());
            }
        } else {
            refereeCards = getRefereeCards();
        }
        return MyNameCardDTO.builder()
                .hasCard(hasCard)
                .applyStatus(applyStatus)
                .nameCard(!hasCard ? null : buildNameCard(carUserDO, nameCardDO, applyStatus))
                .recentlyUsers(recentlyUsers)
                .refereeCards(refereeCards)
                .code(hasCard ? nameCardDO.getCode() : "")
                .build();
    }

    private List<NameCardDTO> getRefereeCards() {
        List<NameCardDTO> refereeCards = Lists.newArrayList();
        List<NameCardDO> nameCardDOS = nameCardDOMapper.selectRefereeCards();
        if (!ObjectUtils.isEmpty(nameCardDOS)) {
            List<Integer> carUserIds = nameCardDOS.stream().map(NameCardDO::getUserId).distinct().collect(Collectors.toList());
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(carUserIds);
            Map<Integer, CarUserDO> map = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            List<CarUserApplyDO> applyDOS = carUserApplyDOMapper.selectBatchByUserId(carUserIds);
            Map<Integer, List<CarUserApplyDO>> applyMap = ObjectUtils.isEmpty(applyDOS) ? Maps.newHashMap() :
                    applyDOS.stream().collect(Collectors.groupingBy(CarUserApplyDO::getUserId));
            refereeCards = nameCardDOS.stream().map(row -> {
                CarUserDO userDO = map.get(row.getUserId());
                List<CarUserApplyDO> applyList = applyMap.get(row.getUserId());
                String status = getApplyStatus(applyList);
                return buildNameCard(userDO, row, status);
            }).collect(Collectors.toList());
        }
        return refereeCards;
    }

    private String getApplyStatus(List<CarUserApplyDO> applyList) {
        String applyStatus = "0";
        if (!ObjectUtils.isEmpty(applyList)) {
            List<Byte> status = applyList.stream().map(CarUserApplyDO::getStatus).distinct().collect(Collectors.toList());
            if (status.contains((byte) 2)) {
                applyStatus = "2";
            } else if (status.contains((byte) 0)) {
                applyStatus = "1";
            } else {
                applyStatus = "0";
            }
        }
        return applyStatus;
    }

    private NameCardDTO buildNameCard(CarUserDO userDO, NameCardDO nameCardDO, String applyStatus) {
        return NameCardDTO.builder()
                .avatar(userDO.getHeadPortrait())
                .companyAddress(nameCardDO.getCompanyAddress())
                .companyName(nameCardDO.getCompanyName())
                .id(nameCardDO.getId())
                .isCarStore(Objects.equals("2", applyStatus))
                .isVip(DateUtil.dateValid(userDO.getVipStartTime(), userDO.getVipEndTime()))
                .jobTitle(nameCardDO.getJobTitle())
                .location(nameCardDO.getLocation())
                .mobile(nameCardDO.getMobile())
                .name(nameCardDO.getName())
                .userId(nameCardDO.getUserId())
                .wechatNo(nameCardDO.getWechatNo())
                .code(nameCardDO.getCode())
                .build();
    }

    public OtherNameCardDTO otherNameCard(Integer cardId, Integer userId) {
        NameCardDO nameCardDO = nameCardDOMapper.selectByPrimaryKey(cardId);
        if (Objects.isNull(nameCardDO)) {
            throw new RuntimeException("名片不存在");
        }
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(nameCardDO.getUserId());
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        List<CarUserApplyDO> carUserApplyDOS = carUserApplyDOMapper.selectByUserId(nameCardDO.getUserId());
        String applyStatus = getApplyStatus(carUserApplyDOS);
        List<PublishDO> publishDOS = publishDOMapper.selectByCarUserId(nameCardDO.getUserId());
        UserDO userDO = null;
        List<PublishImageVideoDO> publishImageVideoDOS = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(publishDOS)) {
            userDO = userDOMapper.selectByPrimaryKey(publishDOS.get(0).getUserId());
            publishImageVideoDOS = publishImageVideoDOMapper.selectByPublishId(publishDOS.get(0).getId());
        }
        List<NameCardImageVideo> nameCardImageVideos = nameCardImageVideoMapper.selectByCardId(cardId);
        List<NameCardFriendDO> friendDOS = nameCardFriendDOMapper.selectByUserId(nameCardDO.getUserId());
        List<Integer> friendCardIds = ObjectUtils.isEmpty(friendDOS) ? Lists.newArrayList() :
                friendDOS.stream().map(NameCardFriendDO::getCardId).distinct().collect(Collectors.toList());
        List<NameCardDO> friendCardDOS = nameCardDOMapper.selectBatchByPrimaryKey(friendCardIds);
        Map<Integer, NameCardDO> cardMap = ObjectUtils.isEmpty(friendCardDOS) ? Maps.newHashMap() :
                friendCardDOS.stream().collect(Collectors.toMap(NameCardDO::getId, row -> row));
        List<Integer> carUserIds = ObjectUtils.isEmpty(friendCardDOS) ? Lists.newArrayList() :
                friendCardDOS.stream().map(NameCardDO::getUserId).distinct().collect(Collectors.toList());
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(carUserIds);
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        if (!Objects.isNull(userId) && !Objects.equals(userId, nameCardDO.getUserId())) {
            this.addLookRecord(userId, cardId);
        }
        return OtherNameCardDTO.builder()
                .nameCard(this.buildNameCard(carUserDO, nameCardDO, applyStatus))
                .introduce(Objects.isNull(nameCardDO.getIntroduce()) ? "" : nameCardDO.getIntroduce())
                .voiceIntroduce(Objects.isNull(nameCardDO.getVoiceIntroduce()) ? null : nameCardDO.getVoiceIntroduce())
                .voiceIntroduceTime(Objects.isNull(nameCardDO.getVoiceIntroduceTime()) ? 0 : nameCardDO.getVoiceIntroduceTime())
                .publish(ObjectUtils.isEmpty(publishDOS) ? null : cov(publishDOS.get(0), userId, userDO, PublishCallSceneEnum.MY_PUBLISH, publishImageVideoDOS, allMap()))
                .friendUsers(friendDOS.stream().map(row -> {
                    NameCardDO friendNameCard = cardMap.get(row.getCardId());
                    CarUserDO user = Objects.isNull(friendNameCard) ? null : userMap.get(friendNameCard.getUserId());
                    return FriendUsers.builder()
                            .avatar(Objects.isNull(user) ? "" : user.getHeadPortrait())
                            .cardId(row.getCardId())
                            .name(Objects.isNull(friendNameCard) ? "" : friendNameCard.getName())
                            .userId(Objects.isNull(friendNameCard) ? 0 : friendNameCard.getUserId())
                            .build();
                }).collect(Collectors.toList()))
                .imageVideos(ObjectUtils.isEmpty(nameCardImageVideos) ? Lists.newArrayList() :
                        nameCardImageVideos.stream().map(row -> PublishImageVideoDTO.builder()
                                .file(row.getFile())
                                .type(row.getType())
                            .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Async
    public void addLookRecord(Integer userId, Integer cardId) {
        nameCardLookRecordDOMapper.insertSelective(NameCardLookRecordDO.builder()
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .userId(userId)
                .cardId(cardId)
                .build());
    }

    public PublishDTO cov(PublishDO row, Integer requestUserId, UserDO publishUser, PublishCallSceneEnum publishCallSceneEnum, List<PublishImageVideoDO> publishImageVideoDOS, Map<Integer, String> cm) {
        publishImageVideoDOS = ObjectUtils.isEmpty(publishImageVideoDOS) ? Lists.newArrayList() : publishImageVideoDOS.stream().sorted(Comparator.comparing(PublishImageVideoDO::getId)).collect(Collectors.toList());

        BrandDO brandDO = row.getBrandId() == null ? null : brandDOMapper.selectByPrimaryKey(row.getBrandId());

        return PublishDTO.builder()
                .categoryFirstId(row.getCategoryFirstId())
                .categoryFirstName(cm.get(row.getCategoryFirstId()))
                .categorySecondId(row.getCategorySecondId())
                .categorySecondName(cm.get(row.getCategorySecondId()))
                .collectionCount(row.getCollectionCount() == null ? 0 : row.getCollectionCount())
                .contactPhone(StringUtils.isEmpty(row.getContactPhone()) ? DefaultHandleConstant.PUBLISH_CONTACT_PHONE : row.getContactPhone())
                .createTime(DateFormatUtils.format(row.getCreateTime(), Constant.DATE_FORMAT))
                .createTimeFriendly(DateUtil.formatDate(row.getCreateTime()))
                .discussCount(row.getDiscussCount())
                .id(row.getId())
                .imageVideos(ObjectUtils.isEmpty(publishImageVideoDOS) ? Lists.newArrayList() : publishImageVideoDOS.stream().map(innerRow -> PublishImageVideoDTO.builder().file(innerRow.getFile()).type(innerRow.getType()).build()).collect(Collectors.toList()))
                .inPrice(row.getInPrice() == null ? DefaultHandleConstant.PUBLISH_IN : String.valueOf(row.getInPrice()))
                .isDeleted(row.getIsDeleted())
                .isDownShelf(getIsDownShelf(row.getShelfStatus()))
                .likeCount(row.getLikeCount() == null ? 0 : row.getLikeCount())
                .location(getUserLocation(row.getProvinceName(), row.getCityName(), row.getAreaName()))
                .locationDetail(getLocationDetail(row))
                .mainMedia(row.getMainMedia())
                .operates(getOperates(row, requestUserId, publishCallSceneEnum))
                .outPrice(row.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : String.valueOf(row.getOutPrice()))
                .phone(StringUtils.isEmpty(row.getPhone()) ? "" : row.getPhone())
                .productiveYear(row.getProductiveYear())
                .publishUserInfo(getUser(publishUser))
                .textIntroduce(StringUtils.isEmpty(row.getTextIntroduce()) ? DefaultHandleConstant.PUBLISH_TEXT_INTRODUCE : row.getTextIntroduce())
                .title(StringUtils.isEmpty(row.getTitle()) ? DefaultHandleConstant.PUBLISH_TITLE : row.getTitle())
                .viewCount(row.getViewCount() == null ? 0 : row.getViewCount())
                .viewPhoneCount(row.getViewPhoneCount())
                .voiceIntroduce(StringUtils.isEmpty(row.getVoiceIntroduce()) ? null : row.getVoiceIntroduce())
                .voiceIntroduceTime(row.getVoiceIntroduceTime())

                .brandId(row.getBrandId())
                .brandName(brandDO == null ? "" : brandDO.getName())
                .usageHours(StringUtils.isEmpty(row.getUsageHours()) ? "" : row.getUsageHours())
                .hasInvoice(row.getHasInvoice())
                .hasCertificate(row.getHasCertificate())
                .contact(StringUtils.isEmpty(row.getContact()) ? "" : row.getContact())

                .build();
    }

    public Boolean getIsDownShelf(Byte shelfStatus) {
        return PublishShelfStatusEnum.byCode(shelfStatus) == null ? true : PublishShelfStatusEnum.isOff(shelfStatus);
    }

    public List<OperateDTO> getOperates(PublishDO row, Integer requestUserId, PublishCallSceneEnum publishCallSceneEnum) {
        List<OperateDTO> data = Lists.newArrayList();
        switch (publishCallSceneEnum) {
            case MY_PUBLISH:
                if (Objects.equals(row.getCarUserId(), requestUserId)) {
                    if (getIsDownShelf(row.getShelfStatus())) {
                        data.addAll(OperateDTO.getOperate(OperateTypeEnum.PUBLISH_RE_ON));
                    } else {
                        data.addAll(OperateDTO.getOperate(OperateTypeEnum.PUBLISH_OFF, OperateTypeEnum.PUBLISH_MODIFY));
                    }
                }
                break;
            case MY_COLLECTION:
                data.addAll(OperateDTO.getOperate(OperateTypeEnum.PUBLISH_REMOVE_COLLECTION));
                break;
            case SQUARE:
            default:
                break;
        }

        return data;
    }

    public String getUserLocation(UserDO userDO) {
        return getUserLocation(userDO.getProvinceName(), userDO.getCityName(), userDO.getAreaName());
    }

    public LocationDetailDTO getLocationDetail(PublishDO row) {
        return LocationDetailDTO.builder()
                .areaCode(row.getAreaCode())
                .areaName(row.getAreaName())
                .cityCode(row.getCityCode())
                .cityName(row.getCityName())
                .location(getUserLocation(row.getProvinceName(), row.getCityName(), row.getAreaName()))
                .provinceCode(row.getProvinceCode())
                .provinceName(row.getProvinceName())
                .build();
    }

    public String getUserLocation(String pName, String cName, String aName) {
        return (org.apache.commons.lang3.StringUtils.isEmpty(pName) ? "" : pName)
                + "·"
                + (org.apache.commons.lang3.StringUtils.isEmpty(cName) ? "" : cName)
                + "·"
                + (org.apache.commons.lang3.StringUtils.isEmpty(aName) ? "" : aName);
    }

    public UserDTO getUser(UserDO userDO, Boolean hasDiscuss) {
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
                    .userIdentity(org.apache.commons.lang3.StringUtils.isEmpty(userDO.getUserIdentity()) ? "" : userDO.getUserIdentity())
                    .viewPhoneCount(userDO.getViewPhoneCount())
                    .build();
        }

        return userDTO;
    }

    public Map<Integer, String> allMap() {
        return categoryDOMapper.selectAll().stream().collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));
    }

    public UserDTO getUser(UserDO userDO) {
        return getUser(userDO, true);
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

    public Boolean applyCarStore(ApplyCarStoreParam param) {
        List<CarUserApplyDO> carUserApplyDOS = carUserApplyDOMapper.selectByUserId(param.getUserId());
        String applyStatus = getApplyStatus(carUserApplyDOS);
        if (!Objects.equals("0", applyStatus)) {
            throw new RuntimeException("不可重复申请");
        }
        carUserApplyDOMapper.insertSelective(CarUserApplyDO.builder()
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .userId(param.getUserId())
                .name(param.getName())
                .mobile(param.getMobile())
                .remark(param.getRemark())
                .status((byte) 0)
                .build());
        return Boolean.TRUE;
    }

    public ChangeNameCardDTO changeNameCard(Integer userId) {
        List<NameCardLookRecordDO> recordDOS = nameCardLookRecordDOMapper.selectByUserId(userId);
        List<NameCardDTO> lookCards = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(recordDOS)) {
            List<NameCardDO> nameCardDO = nameCardDOMapper.selectBatchByPrimaryKey(recordDOS.stream().map(NameCardLookRecordDO::getCardId).distinct().collect(Collectors.toList()));
            Map<Integer, NameCardDO> cardMap = ObjectUtils.isEmpty(nameCardDO) ? Maps.newHashMap() : nameCardDO.stream().collect(Collectors.toMap(NameCardDO::getId, row -> row));
            List<CarUserDO> carUserDOS = ObjectUtils.isEmpty(nameCardDO) ? Lists.newArrayList() :
                    carUserDOMapper.selectBatchByPrimaryKey(nameCardDO.stream().map(NameCardDO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            List<Integer> cardIds = Lists.newArrayList();
            recordDOS.forEach(row -> {
                if (!cardIds.contains(row.getCardId())) {
                    NameCardDO cardDO = cardMap.get(row.getCardId());
                    if (!Objects.isNull(cardDO)) {
                        CarUserDO userDO = userMap.get(cardDO.getUserId());
                        if (!Objects.isNull(userDO)) {
                            List<CarUserApplyDO> carUserApplyDOS = carUserApplyDOMapper.selectByUserId(userDO.getId());
                            String applyStatus = getApplyStatus(carUserApplyDOS);
                            lookCards.add(buildNameCard(userDO, cardDO, applyStatus));
                        }
                    }
                    cardIds.add(row.getCardId());
                }
            });
        }
        return ChangeNameCardDTO.builder()
                .lookCards(lookCards)
                .refereeCards(getRefereeCards())
                .build();
    }

    @Transactional
    public Integer createNameCard(NameCardParam param) {
        Date now = DateUtil.now();
        NameCardDO nameCardDO = NameCardDO.builder()
                .createTime(now)
                .updateTime(now)
                .userId(param.getUserId())
                .name(param.getName())
                .mobile(param.getMobile())
                .wechatNo(param.getWechatNo())
                .companyAddress(param.getCompanyAddress())
                .companyName(param.getCompanyName())
                .jobTitle(param.getJobTitle())
                .location(param.getCompanyLocation())
                .introduce(param.getIntroduce())
                .voiceIntroduce(param.getIntroduceVoice())
                .voiceIntroduceTime(param.getIntroduceVoiceTime())
                .code(produceNumber(6))
                .build();
        nameCardDOMapper.insertSelective(nameCardDO);
        List<NameCardImageVideo> nameCardImageVideos = Lists.newArrayList();
        List<NameCardFriendDO> nameCardFriendDOS = Lists.newArrayList();
        List<FriendCardRecordDO> friendCardRecordDOS = Lists.newArrayList();

        if (!ObjectUtils.isEmpty(param.getFiles())) {
            nameCardImageVideos.addAll(param.getFiles().stream().map(row -> NameCardImageVideo.builder()
                    .createTime(now)
                    .updateTime(now)
                    .cardId(nameCardDO.getId())
                    .file(row.getFile())
                    .type(row.getType())
                    .build())
            .collect(Collectors.toList()));
        }

        if (!ObjectUtils.isEmpty(param.getFriendCardIds())) {
            nameCardFriendDOS.addAll(param.getFriendCardIds().stream().map(row -> NameCardFriendDO.builder()
                    .createTime(now)
                    .updateTime(now)
                    .userId(param.getUserId())
                    .cardId(row)
                    .build())
            .collect(Collectors.toList()));

            friendCardRecordDOS.addAll(param.getFriendCardIds().stream().map(row -> FriendCardRecordDO.builder()
                    .createTime(now)
                    .updateTime(now)
                    .userId(param.getUserId())
                    .cardId(row)
                    .build())
            .collect(Collectors.toList()));
        }

        if (!ObjectUtils.isEmpty(nameCardImageVideos)) {
            nameCardImageVideoMapper.insertBatchSelective(nameCardImageVideos);
        }

        if (!ObjectUtils.isEmpty(nameCardFriendDOS)) {
            nameCardFriendDOMapper.insertBatchSelective(nameCardFriendDOS);
        }

        if (!ObjectUtils.isEmpty(friendCardRecordDOS)) {
            friendCardRecordDOMapper.insertBatchSelective(friendCardRecordDOS);
        }
        return nameCardDO.getId();
    }

    @Transactional
    public Boolean modifyNameCard(NameCardParam param) {
        NameCardDO nameCardDO = nameCardDOMapper.selectByPrimaryKey(param.getCardId());
        if (Objects.isNull(nameCardDO)) {
            throw new RuntimeException("名片不存在");
        }
        Date now = DateUtil.now();
        NameCardDO update = NameCardDO.builder()
                .id(param.getCardId())
                .updateTime(now)
                .name(param.getName())
                .mobile(param.getMobile())
                .wechatNo(param.getWechatNo())
                .companyAddress(param.getCompanyAddress())
                .companyName(param.getCompanyName())
                .jobTitle(param.getJobTitle())
                .location(param.getCompanyLocation())
                .introduce(param.getIntroduce())
                .voiceIntroduce(param.getIntroduceVoice())
                .voiceIntroduceTime(param.getIntroduceVoiceTime())
                .build();
        nameCardDOMapper.updateByPrimaryKeySelective(update);
        List<NameCardImageVideo> nameCardImageVideos = Lists.newArrayList();
        List<NameCardFriendDO> nameCardFriendDOS = Lists.newArrayList();
        List<FriendCardRecordDO> friendCardRecordDOS = Lists.newArrayList();

        nameCardImageVideoMapper.deleteByCardId(param.getCardId());
        if (!ObjectUtils.isEmpty(param.getFiles())) {
            nameCardImageVideos.addAll(param.getFiles().stream().map(row -> NameCardImageVideo.builder()
                    .createTime(now)
                    .updateTime(now)
                    .cardId(nameCardDO.getId())
                    .file(row.getFile())
                    .type(row.getType())
                    .build())
                    .collect(Collectors.toList()));
        }
        nameCardFriendDOMapper.deleteByUserId(param.getUserId());
        if (!ObjectUtils.isEmpty(param.getFriendCardIds())) {
            nameCardFriendDOS.addAll(param.getFriendCardIds().stream().map(row -> NameCardFriendDO.builder()
                    .createTime(now)
                    .updateTime(now)
                    .userId(param.getUserId())
                    .cardId(row)
                    .build())
                    .collect(Collectors.toList()));

            List<FriendCardRecordDO> recordDOS = friendCardRecordDOMapper.selectByUserId(param.getUserId());
            List<Integer> cardIds = ObjectUtils.isEmpty(recordDOS) ? Lists.newArrayList() :
                    recordDOS.stream().map(FriendCardRecordDO::getCardId).distinct().collect(Collectors.toList());
            param.getFriendCardIds().forEach(row -> {
                if (!cardIds.contains(row)) {
                    friendCardRecordDOS.add(FriendCardRecordDO.builder()
                            .cardId(row)
                            .userId(param.getUserId())
                            .createTime(now)
                            .updateTime(now)
                            .build());
                }
            });
        }

        if (!ObjectUtils.isEmpty(nameCardImageVideos)) {
            nameCardImageVideoMapper.insertBatchSelective(nameCardImageVideos);
        }

        if (!ObjectUtils.isEmpty(nameCardFriendDOS)) {
            nameCardFriendDOMapper.insertBatchSelective(nameCardFriendDOS);
        }

        if (!ObjectUtils.isEmpty(friendCardRecordDOS)) {
            friendCardRecordDOMapper.insertBatchSelective(friendCardRecordDOS);
        }
        return Boolean.TRUE;
    }

    public List<NameCardSimpleDTO> searchFriendCards(String cardId, Integer userId) {
        List<NameCardFriendDO> nameCardFriendDOS = nameCardFriendDOMapper.selectByUserId(userId);
        List<Integer> currentCardIds = ObjectUtils.isEmpty(nameCardFriendDOS) ? Lists.newArrayList() :
                nameCardFriendDOS.stream().map(NameCardFriendDO::getCardId).distinct().collect(Collectors.toList());
        List<NameCardDO> nameCardDOS = nameCardDOMapper.selectByCardNo(cardId);
        return buildSimpleNameCard(nameCardDOS, currentCardIds);
    }

    public List<NameCardSimpleDTO> historyFriendCards(Integer userId) {
        List<NameCardFriendDO> nameCardFriendDOS = nameCardFriendDOMapper.selectByUserId(userId);
        List<FriendCardRecordDO> recordDOS = friendCardRecordDOMapper.selectByUserId(userId);
        List<NameCardSimpleDTO> historyCards = Lists.newArrayList();
        List<Integer> currentCardIds = ObjectUtils.isEmpty(nameCardFriendDOS) ? Lists.newArrayList() :
                nameCardFriendDOS.stream().map(NameCardFriendDO::getCardId).distinct().collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(recordDOS)) {
            List<NameCardDO> nameCardDOS = nameCardDOMapper.selectBatchByPrimaryKey(
                    recordDOS.stream().map(FriendCardRecordDO::getCardId).distinct().collect(Collectors.toList()));
            historyCards = buildSimpleNameCard(nameCardDOS, currentCardIds);
        }
        return historyCards;
    }

    private List<NameCardSimpleDTO> buildSimpleNameCard(List<NameCardDO> nameCardDOS, List<Integer> currentCardIds) {
        List<NameCardSimpleDTO> result = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(nameCardDOS)) {
            Map<Integer, NameCardDO> cardMap = ObjectUtils.isEmpty(nameCardDOS) ? Maps.newHashMap() :
                    nameCardDOS.stream().collect(Collectors.toMap(NameCardDO::getId, row -> row));
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(nameCardDOS.stream().map(
                    NameCardDO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            result = nameCardDOS.stream().map(row -> {
                CarUserDO userDO = userMap.get(row.getUserId());
                NameCardDO cardDO = cardMap.get(row.getId());
                return NameCardSimpleDTO.builder()
                        .name(Objects.isNull(cardDO.getName()) ? "" : cardDO.getName())
                        .cardId(row.getId())
                        .avatar(Objects.isNull(userDO) ? "" : userDO.getHeadPortrait())
                        .hasAdd(currentCardIds.contains(row.getId()))
                        .code(row.getCode())
                        .jobTitle(row.getJobTitle())
                        .companyName(row.getCompanyName())
                        .companyAddress(row.getCompanyAddress())
                        .mobile(row.getMobile())
                        .build();
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Transactional
    public Boolean addFriend(Integer cardId, Integer userId) {
        NameCardFriendDO nameCardFriendDO = nameCardFriendDOMapper.selectByUserIdAndCardId(userId, cardId);
        if (Objects.isNull(nameCardFriendDO)) {
            nameCardFriendDOMapper.insertSelective(NameCardFriendDO.builder()
                    .cardId(cardId)
                    .userId(userId)
                    .createTime(DateUtil.now())
                    .updateTime(DateUtil.now())
                    .build());
        }
        FriendCardRecordDO recordDO = friendCardRecordDOMapper.selectByUserIdAndCardId(userId, cardId);
        if (Objects.isNull(recordDO)) {
            friendCardRecordDOMapper.insertSelective(FriendCardRecordDO.builder()
                    .cardId(cardId)
                    .userId(userId)
                    .createTime(DateUtil.now())
                    .updateTime(DateUtil.now())
                    .build());
        }
        return Boolean.TRUE;
    }

    public Boolean takeMobile(Integer userId, Integer cardId) {
        nameCardLookRecordDOMapper.updateDialByCardIdAndUserId(userId, cardId, "1");
        return Boolean.TRUE;
    }

    /**
     * 随机产生几位数字
     */
    private String produceNumber(int maxLength) {
        return RandomStringUtils.randomNumeric(maxLength);
    }
}
