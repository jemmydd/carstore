package com.luoyanjie.mechanical.service.global;

import com.luoyanjie.mechanical.bean.dto.global.UploadDTO;
import com.luoyanjie.mechanical.bean.dto.global.UploadWithFirstDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GlobalService {
    UploadDTO upload(MultipartFile file) throws IOException;

    List<UploadDTO> uploads(List<MultipartFile> files) throws IOException;

    UploadWithFirstDTO uploadWithFirst(MultipartFile file) throws Exception;
}
