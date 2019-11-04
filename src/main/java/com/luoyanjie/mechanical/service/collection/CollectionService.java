package com.luoyanjie.mechanical.service.collection;

import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.component.result.PageData;

public interface CollectionService {
    PageData<PublishDTO> getPageData(Integer pageNum, Integer pageSize, Integer userId);

    Boolean toCollect(Integer userId, Integer publishId);

    Boolean unCollect(Integer userId, Integer publishId);
}
