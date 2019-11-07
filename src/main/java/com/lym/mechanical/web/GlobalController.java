package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.global.UploadDTO;
import com.lym.mechanical.bean.dto.global.UploadWithFirstDTO;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.global.GlobalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "全局公共")
@Slf4j
@RestController
@RequestMapping("global")
public class GlobalController {
    @Autowired
    private GlobalService globalService;

    @ApiOperation(value = "单个上传文件")
    @PostMapping(value = "upload")
    public Result<UploadDTO> upload(@RequestParam("file") @ApiParam(value = "文件", required = true) MultipartFile file)
            throws IOException {
        return ResultUtil.success(globalService.upload(file));
    }

    @ApiOperation(value = "批量上传文件")
    @PostMapping(value = "uploads")
    public Result<List<UploadDTO>> uploads(@RequestParam("files") @ApiParam(value = "文件", required = true) List<MultipartFile> files)
            throws IOException {
        return ResultUtil.success(globalService.uploads(files));
    }

    @ApiOperation(value = "单个上传文件-同时获取视频首帧图")
    @PostMapping(value = "uploadWithFirst")
    public Result<UploadWithFirstDTO> uploadWithFirst(@RequestParam("file") @ApiParam(value = "文件", required = true) MultipartFile file)
            throws Exception {
        return ResultUtil.success(globalService.uploadWithFirst(file));
    }
}
