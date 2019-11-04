package com.luoyanjie.mechanical.service.invite;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.common.Constant;
import com.luoyanjie.mechanical.bean.dto.invite.InviteDTO;
import com.luoyanjie.mechanical.bean.entity.InviteDO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.InviteDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.sys.ExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-08-25 16:23
 * Coding happily every day!
 */
@Slf4j
@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    private InviteDOMapper inviteDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserService userService;

    @Override
    public Boolean inviteBind(Integer fromId, Integer toId) {
        if (fromId == null) throw new RuntimeException("邀请者ID不能为空");
        if (toId == null) throw new RuntimeException("被邀请者ID不能为空");

        List<UserDO> userDOList = userDOMapper.selectBatchByPrimaryKey(Lists.newArrayList(fromId, toId));
        if (ObjectUtils.isEmpty(userDOList) || userDOList.size() < 2) throw new RuntimeException("入参不正确");

        inviteDOMapper.insertSelective(
                InviteDO.builder()
                        .fromId(fromId)
                        .toId(toId)
                        .build()
        );

        userDOMapper.addInviteCount(fromId);

        return true;
    }

    @Override
    public PageData<InviteDTO> pageData(Integer pageNum, Integer pageSize, Integer userId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "邀请者ID"));
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<InviteDO> pd = (Page<InviteDO>) inviteDOMapper.selectByFrom(userId);

        List<InviteDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(InviteDO::getToId).distinct().collect(Collectors.toList()));

        return PageData.data(pd, data.stream().map(row -> cov(row, userDOMap.get(row.getToId()))).collect(Collectors.toList()));
    }

    private InviteDTO cov(InviteDO row, UserDO userDO) {
        return InviteDTO.builder()
                .invite(userService.getUser(userDO))
                .inviteTime(row == null ? "" : DateFormatUtils.format(row.getCreateTime(), Constant.DATE_FORMAT))
                .build();
    }
}
