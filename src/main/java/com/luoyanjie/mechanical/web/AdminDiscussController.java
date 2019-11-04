package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.discuss.AdminDiscussDTO;
import com.luoyanjie.mechanical.bean.dto.discuss.DiscussDTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.param.discuss.ModifyDiscussParam;
import com.luoyanjie.mechanical.bean.param.publish.AdminPublishSubmitParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.dao.mapper.DiscussDOMapper;
import com.luoyanjie.mechanical.service.discuss.DiscussService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-08-14 23:06
 * Coding happily every day!
 */
@Api(tags = "ADMIN-评论")
@Slf4j
@RestController
@RequestMapping("adminDiscuss")
public class AdminDiscussController {
    @Autowired
    private DiscussService discussService;

    @ApiOperation(value = "查询评论")
    @GetMapping("searchForAdmin.admin")
    public Result<PageData<AdminDiscussDTO>> searchForAdmin(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "标题") @RequestParam(value = "title", required = false) String title
    ) {
        return ResultUtil.success(discussService.searchForAdmin(pageNum, pageSize, title));
    }

    @ApiOperation(value = "删除一个评论")
    @PostMapping("deleteDiscuss.admin")
    public Result<Boolean> deletePublish(@ApiParam(value = "评论ID") @RequestParam(value = "discussId") Integer discussId) {
        return ResultUtil.success(discussService.deleteDiscuss(discussId));
    }

    @ApiOperation(value = "修改一个评论")
    @PostMapping("modifyDiscuss.admin")
    public Result<Boolean> modifyPublish(@RequestBody ModifyDiscussParam param) {
        return ResultUtil.success(discussService.modifyDiscuss(param));
    }
}
