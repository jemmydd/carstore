package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.admin.AdminUserApplyDTO;
import com.lym.mechanical.bean.entity.CarUserApplyDO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.MessageDO;
import com.lym.mechanical.bean.param.admin.AdminApplyOperateParam;
import com.lym.mechanical.bean.param.admin.AdminApplySearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CarUserApplyDOMapper;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.MessageDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-21 17:36
 * Good good code, day day up!
 **/
@Service
public class AdminUserApplyService {

    @Autowired
    private CarUserApplyDOMapper carUserApplyDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private MessageDOMapper messageDOMapper;

    public PageData<AdminUserApplyDTO> list(AdminApplySearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<CarUserApplyDO> carUserApplyDOS = (Page<CarUserApplyDO>) carUserApplyDOMapper.selectForWeb(param.getStartTime(), param.getEndTime());
        List<CarUserApplyDO> data = carUserApplyDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(param.getPageSize());
        }

        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(ObjectUtils.isEmpty(data) ? Lists.newArrayList() :
                data.stream().map(CarUserApplyDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return PageData.data(carUserApplyDOS, data.stream().map(row -> {
            CarUserDO carUserDO = userMap.get(row.getUserId());
            return AdminUserApplyDTO.builder()
                    .id(row.getId())
                    .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                    .nickName(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                    .mobile(row.getMobile())
                    .name(row.getName())
                    .remark(row.getRemark())
                    .applyTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .build();
        }).collect(Collectors.toList()));
    }

    public Boolean operate(AdminApplyOperateParam param) {
        CarUserApplyDO carUserApplyDO = carUserApplyDOMapper.selectByPrimaryKey(param.getId());
        if (Objects.isNull(carUserApplyDO)) {
            throw new RuntimeException("申请不存在");
        }
        if (!Objects.equals((byte) 0, carUserApplyDO.getStatus())) {
            throw new RuntimeException("申请已完成");
        }
        carUserApplyDOMapper.updateByPrimaryKeySelective(CarUserApplyDO.builder()
                .id(param.getId())
                .updateTime(DateUtil.now())
                .status(Objects.equals("0", param.getType()) ? (byte) 1 : (byte) 2)
                .build());
        messageDOMapper.insertSelective(
                MessageDO.builder()
                        .createTime(DateUtil.now())
                        .updateTime(DateUtil.now())
                        .fromCarUserId(0)
                        .toCarUserId(carUserApplyDO.getUserId())
                        .content(Objects.equals("0", param.getType()) ? ("很抱歉，您提交的车商申请审核不通过，原因为：" + param.getReason()) : "恭喜，您提交的车商申请已经审核通过！")
                        .type("TEXT")
                        .userGroup("0-" + carUserApplyDO.getUserId())
                        .isRead(Boolean.FALSE)
                        .build()
        );
        return Boolean.TRUE;
    }
}
