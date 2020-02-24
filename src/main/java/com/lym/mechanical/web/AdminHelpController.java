package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminFeedbackDTO;
import com.lym.mechanical.bean.dto.admin.AdminQuestionDTO;
import com.lym.mechanical.bean.param.admin.AdminFeedbackDealParam;
import com.lym.mechanical.bean.param.admin.AdminFeedbackSearchParam;
import com.lym.mechanical.bean.param.admin.AdminQuestionParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminHelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyimin
 * @create 2020-02-24 15:34
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminHelp")
@Api(tags = "帮助与反馈")
public class AdminHelpController {

    @Autowired
    private AdminHelpService adminHelpService;

    @GetMapping("questionList.admin")
    @ApiOperation(value = "常见问题列表")
    public Result<PageData<AdminQuestionDTO>> questionList(@RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                           @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminHelpService.questionList(pageNum, pageSize));
    }

    @PostMapping("addQuestion.admin")
    @ApiOperation(value = "新增常见问题")
    public Result<Boolean> addQuestion(@RequestBody AdminQuestionParam param) {
        return ResultUtil.success(adminHelpService.addQuestion(param));
    }

    @PostMapping("modifyQuestion.admin")
    @ApiOperation(value = "编辑常见问题")
    public Result<Boolean> modifyQuestion(@RequestBody AdminQuestionParam param) {
        return ResultUtil.success(adminHelpService.modifyQuestion(param));
    }

    @GetMapping("deleteQuestion.admin")
    @ApiOperation(value = "删除常见问题")
    public Result<Boolean> deleteQuestion(@RequestParam("id") @ApiParam(value = "问题id") Integer id) {
        return ResultUtil.success(adminHelpService.deleteQuestion(id));
    }

    @PostMapping("feedbackList.admin")
    @ApiOperation(value = "反馈列表")
    public Result<PageData<AdminFeedbackDTO>> feedbackList(@RequestBody AdminFeedbackSearchParam param) {
        return ResultUtil.success(adminHelpService.feedbackList(param));
    }

    @PostMapping("dealFeedback.admin")
    @ApiOperation(value = "处理意见反馈")
    public Result<Boolean> dealFeedback(@RequestBody AdminFeedbackDealParam param) {
        return ResultUtil.success(adminHelpService.dealFeedback(param));
    }
}
