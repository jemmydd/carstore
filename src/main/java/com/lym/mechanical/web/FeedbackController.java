package com.lym.mechanical.web;

import com.lym.mechanical.bean.entity.QuestionAnswerDO;
import com.lym.mechanical.bean.param.feedback.FeedbackParam;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.feedback.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname FeedbackController
 * @Description
 * @Date 2020/1/14 18:11
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("feedback")
@Api("帮助与反馈")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("questionAnswer")
    @ApiOperation(value = "常见问题")
    public Result<List<QuestionAnswerDO>> questionAndAnswer() {
        return ResultUtil.success(feedbackService.questionAnswers());
    }

    @PostMapping("feedback")
    @ApiOperation(value = "提交反馈")
    public Result<Boolean> feedback(@RequestBody FeedbackParam param) {
        return ResultUtil.success(feedbackService.feedback(param));
    }
}
