package com.luoyanjie.mechanical.service.discuss;

import com.luoyanjie.mechanical.bean.dto.discuss.AdminDiscussDTO;
import com.luoyanjie.mechanical.bean.dto.discuss.DiscussDTO;
import com.luoyanjie.mechanical.bean.param.discuss.ModifyDiscussParam;
import com.luoyanjie.mechanical.bean.param.discuss.SendDiscussParam;
import com.luoyanjie.mechanical.component.result.PageData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DiscussService {
    PageData<DiscussDTO> getPageDataWithOutRequest(Integer pageNum, Integer pageSize, Integer userId);

    Boolean toRead(Integer userId, Integer publishId, Integer discussId);

    Boolean sendDiscuss(SendDiscussParam param);

    PageData<AdminDiscussDTO> searchForAdmin(Integer pageNum, Integer pageSize, String title);

    Boolean deleteDiscuss(Integer discussId);

    Boolean modifyDiscuss(ModifyDiscussParam param);

    Boolean toReadBatch(List<Integer> ids);
}
