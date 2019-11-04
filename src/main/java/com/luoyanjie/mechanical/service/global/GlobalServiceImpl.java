package com.luoyanjie.mechanical.service.global;

import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.dto.global.UploadDTO;
import com.luoyanjie.mechanical.bean.dto.global.UploadWithFirstDTO;
import com.luoyanjie.mechanical.component.oss.OssService;
import com.luoyanjie.mechanical.component.oss.OssServiceImpl;
import com.luoyanjie.mechanical.sys.ExceptionAdvice;
import com.luoyanjie.mechanical.sys.FileDomain;
import com.luoyanjie.mechanical.util.VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-07-29 16:15
 * Coding happily every day!
 */
@Slf4j
@Service
public class GlobalServiceImpl implements GlobalService {
    @Autowired
    private OssService ossService;

    @Autowired
    private FileDomain fileDomain;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public UploadDTO upload(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "上传文件"));

        String originalFilename = multipartFile.getOriginalFilename();

        UploadDTO uploadDTO = UploadDTO.builder()
                .originalFilename(originalFilename)
                .uploadResult(false)
                .url(null)
                .build();

        if (!StringUtils.isEmpty(originalFilename)) {
            String ex = originalFilename.substring(originalFilename.indexOf("."));
            String key = OssServiceImpl.key() + ex;

            ossService.upload(key, multipartFile.getInputStream());

            uploadDTO.setUploadResult(true);
            uploadDTO.setUrl(key);
            uploadDTO.setDomainUrl(fileDomain.addDomain(key));
        }

        return uploadDTO;
    }

    @Override
    public List<UploadDTO> uploads(List<MultipartFile> files) throws IOException {
        if (ObjectUtils.isEmpty(files)) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "上传文件"));

        List<UploadDTO> r = Lists.newArrayList();

        for (MultipartFile multipartFile : files) {
            r.add(upload(multipartFile));
        }

        return r;
    }

    @Override
    public UploadWithFirstDTO uploadWithFirst(MultipartFile file) throws Exception {
        UploadDTO uploadDTO = upload(file);//普通上传

        File fileFirst = VideoUtil.getFirstImg(file.getInputStream());

        String ex = fileFirst.getName().substring(fileFirst.getName().indexOf("."));
        String key = OssServiceImpl.key() + ex;

        String firstImgUrl = ossService.upload(key, new FileInputStream(fileFirst));

        if (fileFirst.exists())
            fileFirst.delete();

        return UploadWithFirstDTO.builder()
                .domainUrl(uploadDTO.getDomainUrl())
                .firstPageUrl(fileDomain.addDomain(firstImgUrl))
                .originalFilename(uploadDTO.getOriginalFilename())
                .uploadResult(uploadDTO.getUploadResult())
                .url(uploadDTO.getUrl())
                .build();
    }
}
