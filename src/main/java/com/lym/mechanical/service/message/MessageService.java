package com.lym.mechanical.service.message;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.common.DefaultHandleConstant;
import com.lym.mechanical.bean.dto.card.RecentlyUserDTO;
import com.lym.mechanical.bean.dto.message.*;
import com.lym.mechanical.bean.entity.*;
import com.lym.mechanical.bean.enumBean.MessageTypeEnum;
import com.lym.mechanical.bean.param.message.MessageSendParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.*;
import com.lym.mechanical.util.DateUtil;
import com.lym.mechanical.websocket.MessageWebSocket;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname MessageService
 * @Description
 * @Date 2019/11/8 17:38
 * @Created by jimy
 * good good code, day day up!
 */
@Service
public class MessageService {

    @Autowired
    private MessageDOMapper messageDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private NameCardDOMapper nameCardDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private NameCardLookRecordDOMapper nameCardLookRecordDOMapper;

    @Autowired
    private IntentionCustomDOMapper intentionCustomDOMapper;

    public List<MessageDTO> list(Integer userId, String name) {
        List<MessageDTO> result = Lists.newArrayList();
        List<MessageDO> messageDOS = messageDOMapper.selectByUserId(userId, name);
        if (!ObjectUtils.isEmpty(messageDOS)) {
            List<MessageDO> messageList = messageDOMapper.selectByToUserId(userId);
            List<Integer> userIds = Lists.newArrayList();
            messageDOS.forEach(row -> {
                Integer otherUserId = Objects.equals(userId, row.getFromCarUserId()) ? row.getToCarUserId() :
                        row.getFromCarUserId();
                if (!userIds.contains(otherUserId)) {
                    userIds.add(otherUserId);
                }
            });
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(userIds);
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            List<NameCardDO> nameCardDOS = nameCardDOMapper.selectBatchByUserId(userIds);
            Map<Integer, NameCardDO> nameCardMap = ObjectUtils.isEmpty(nameCardDOS) ? Maps.newHashMap() :
                    nameCardDOS.stream().collect(Collectors.toMap(NameCardDO::getUserId, row -> row));
            for (MessageDO messageDO : messageDOS) {
                Integer otherUserId = Objects.equals(userId, messageDO.getFromCarUserId()) ? messageDO.getToCarUserId() :
                        messageDO.getFromCarUserId();
                CarUserDO otherUser = userMap.get(otherUserId);
                NameCardDO nameCardDO = nameCardMap.get(otherUserId);
                List<MessageDO> notReadMessages = ObjectUtils.isEmpty(messageList) ? Lists.newArrayList() :
                        messageList.stream().filter(row -> Objects.equals(row.getFromCarUserId(), otherUserId) && !row.getIsRead())
                        .collect(Collectors.toList());
                result.add(MessageDTO.builder()
                        .avatar(Objects.isNull(otherUser) ? "" : otherUser.getHeadPortrait())
                        .message(Objects.equals(messageDO.getType(), MessageTypeEnum.TEXT.getCode()) ? messageDO.getContent()
                                : MessageTypeEnum.getNameByCode(messageDO.getType()))
                        .name(Objects.isNull(nameCardDO) ? "" : nameCardDO.getName())
                        .time(DateUtil.getDateStr(messageDO.getCreateTime()))
                        .userId(otherUserId)
                        .notReadCount(ObjectUtils.isEmpty(notReadMessages) ? 0 : notReadMessages.size())
                        .build());
            }
        }
        return result;
    }

    public MessageDetailDTO messageDetail(Integer userId, Integer otherUserId, Integer pageNum, Integer pageSize) {
        NameCardDO nameCardDO = nameCardDOMapper.selectByUserId(otherUserId);
        PageData<MessageDetailListDTO> details;
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        String userGroup = userId < otherUserId ? (userId + "-" + otherUserId) : (otherUserId + "-" + userId);
        Page<MessageDO> messageDOS = (Page<MessageDO>) messageDOMapper.selectByUserGroup(userGroup);

        List<MessageDO> data = messageDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            details = PageData.noData(pageSize);
        } else {
            CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(otherUserId);
            details = PageData.data(messageDOS, data.stream().map(row -> {
                MessagePublishDTO publish = new MessagePublishDTO();
                if (Objects.equals(row.getType(), MessageTypeEnum.PUBLISH.getCode())) {
                    PublishDO publishDO = publishDOMapper.selectByPrimaryKey(Integer.parseInt(row.getContent()));
                    if (!Objects.isNull(publishDO)) {
                        publish = MessagePublishDTO.builder()
                                .title(publishDO.getTitle())
                                .date(DateFormatUtils.format(publishDO.getCreateTime(), Constant.DATE_FORMAT))
                                .image(publishDO.getMainMedia())
                                .price(publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice())
                                .build();
                    }
                }
                return MessageDetailListDTO.builder()
                        .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                        .content(Objects.equals(row.getType(), MessageTypeEnum.PUBLISH.getCode()) ? "" : row.getContent())
                        .isSelf(Objects.equals(row.getFromCarUserId(), userId))
                        .publish(publish)
                        .timeStamp(row.getCreateTime().getTime())
                        .type(row.getType())
                        .build();
            }).collect(Collectors.toList()));
        }
        messageDOMapper.updateReadByToUserIdAndFromUserId(userId, otherUserId);
        IntentionCustomDO customDO = intentionCustomDOMapper.selectByUserIdAndOtherId(userId, otherUserId);
        return MessageDetailDTO.builder()
                .mobile(Objects.isNull(nameCardDO) ? "" : nameCardDO.getMobile())
                .wechatNo(Objects.isNull(nameCardDO) ? "" : nameCardDO.getWechatNo())
                .userId(otherUserId)
                .messages(details)
                .cardId(Objects.isNull(nameCardDO) ? null : nameCardDO.getId())
                .hasIntention(!Objects.isNull(customDO))
                .build();
    }

    public Boolean sendMessage(MessageSendParam param) {
        messageDOMapper.insertSelective(MessageDO.builder()
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .fromCarUserId(param.getUserId())
                .toCarUserId(param.getOtherUserId())
                .content(param.getContent())
                .type(param.getType())
                .userGroup(param.getUserId() < param.getOtherUserId() ? (param.getUserId() + "-" + param.getOtherUserId()) :
                        (param.getOtherUserId() + "-" + param.getUserId()))
                .build());
        // 通知对方用户有新消息
        MessageWebSocket.onMessage(param.getOtherUserId().toString());
        return Boolean.TRUE;
    }

    public PageData<LookRecordDTO> lookRecord(Integer userId, Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<LookUserDTO> lookUserDTOS = (Page<LookUserDTO>) nameCardLookRecordDOMapper.selectLookRecordByUserId(userId);

        List<LookUserDTO> data = lookUserDTOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(pageSize);
        }

        List<Integer> userIds = data.stream().map(LookUserDTO::getUserId).distinct().collect(Collectors.toList());
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(userIds);
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        List<Integer> publishIds = data.stream().filter(row -> Objects.equals("PUBLISH_LOOK", row.getBizType()) || Objects.equals("PUBLISH_COLLECT", row.getBizType()))
                .map(LookUserDTO::getBizId).collect(Collectors.toList());
        List<PublishDO> publishDOS = publishDOMapper.selectBatchByPrimaryKey(publishIds);
        Map<Integer, PublishDO> publishMap = ObjectUtils.isEmpty(publishDOS) ? Maps.newHashMap() :
                publishDOS.stream().collect(Collectors.toMap(PublishDO::getId, row -> row));
        return PageData.data(lookUserDTOS, data.stream().map(row -> {
            CarUserDO carUserDO = userMap.get(row.getUserId());
            String desc = Objects.isNull(carUserDO) ? "" : carUserDO.getNickName();
            if (Objects.equals("CARD", row.getBizType())) {
                desc += "查看了你的名片";
            } else {
                PublishDO publishDO = publishMap.get(row.getBizId());
                if (Objects.equals("PUBLISH_LOOK", row.getBizType())) {
                    desc += "查看了你的";
                } else {
                    desc += "收藏了你的";
                }
                desc += ("设备【" + (Objects.isNull(publishDO) ? "" : publishDO.getTitle()) + "】");
            }
            return LookRecordDTO.builder()
                    .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                    .cardId(Objects.equals("CARD", row.getBizType()) ? row.getBizId() : null)
                    .desc(desc)
                    .publishId(Objects.equals("PUBLISH", row.getBizType()) ? row.getBizId() : null)
                    .time(DateUtil.getDateStr(row.getDate()))
                    .userId(row.getUserId())
                    .build();
        }).collect(Collectors.toList()));
    }

//    public PageData<RecentlyUserDTO> askStatistic(Integer userId, Integer pageNum, Integer pageSize) {
//        PageData.checkPageParam(pageNum, pageSize);
//        PageHelper.startPage(pageNum, pageSize);
//
//        Page<String> userGroups = (Page<String>) messageDOMapper.selectRecentlyUsers(userId);
//        List<String> data = userGroups.getResult();
//        if (ObjectUtils.isEmpty(data)) {
//            return PageData.noData(pageSize);
//        } else {
//            List<Integer> userIds = ObjectUtils.isEmpty(userGroups) ? Lists.newArrayList() :
//                    userGroups.stream().map(row -> {
//                        String[] s = row.split("-");
//                        return Objects.equals(s[0], userId.toString()) ? Integer.parseInt(s[1]) : Integer.parseInt(s[0]);
//                    }).collect(Collectors.toList());
//            if (!ObjectUtils.isEmpty(userIds)) {
//                List<RecentlyUserDTO> recentlyUsers = Lists.newArrayList();
//                List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(userIds);
//                Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
//                        carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
//                List<MessageDO> messageDOS = messageDOMapper.selectBatchByUserGroup(userGroups);
//                Map<String, List<MessageDO>> map = ObjectUtils.isEmpty(messageDOS) ? Maps.newHashMap() :
//                        messageDOS.stream().collect(Collectors.groupingBy(MessageDO::getUserGroup));
//                List<String> groups = Lists.newArrayList();
//                messageDOS.forEach(row -> {
//                    if (!groups.contains(row.getUserGroup())) {
//                        groups.add(row.getUserGroup());
//                        CarUserDO userDO = userMap.get(Objects.equals(row.getFromCarUserId(), userId) ?
//                                row.getToCarUserId() : row.getFromCarUserId());
//                        List<MessageDO> messages = map.get(row.getUserGroup());
//                        if (!ObjectUtils.isEmpty(messages)) {
//                            messages = messages.stream().sorted((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())).collect(Collectors.toList());
//                        }
//                        recentlyUsers.add(RecentlyUserDTO.builder()
//                                .avatar(Objects.isNull(userDO) ? "" : userDO.getHeadPortrait())
//                                .userId(Objects.equals(row.getFromCarUserId(), userId) ?
//                                        row.getToCarUserId() : row.getFromCarUserId())
//                                .desc("最近一次互动" + (ObjectUtils.isEmpty(messages) ? "" : DateUtil.formatDate(messages.get(0).getCreateTime(), "yyyy-MM-dd")))
//                                .title((Objects.isNull(userDO) ? "" : userDO.getNickName()) + "跟你互动" + (ObjectUtils.isEmpty(messages) ? "0" : messages.size()) + "次")
//                                .build());
//                    }
//                });
//                return PageData.data(userGroups, recentlyUsers);
//            } else {
//                return PageData.noData(pageSize);
//            }
//        }
//    }

    public Boolean makeTag(Integer userId, Integer otherUserId) {
        IntentionCustomDO intentionCustomDO = intentionCustomDOMapper.selectByUserIdAndOtherId(userId, otherUserId);
        if (Objects.isNull(intentionCustomDO)) {
            intentionCustomDOMapper.insertSelective(IntentionCustomDO.builder()
                    .createTime(DateUtil.now())
                    .updateTime(DateUtil.now())
                    .userId(userId)
                    .intentionCustomUserId(otherUserId)
                    .build());
        } else {
            intentionCustomDOMapper.deleteByPrimaryKey(intentionCustomDO.getId());
        }
        return Boolean.TRUE;
    }

    public Boolean allRead(Integer userId) {
        messageDOMapper.updateReadByToUserIdAndFromUserId(userId, null);
        return Boolean.TRUE;
    }
}
