package com.yiyulihua.oss.controller;

import com.yiyulihua.common.utils.R;
import com.yiyulihua.oss.service.OssService;
import com.yiyulihua.oss.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 文件上传
 *
 * @author sunbo
 * @date 2022-07-18-17:45
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    private final OssService ossService;

    @Autowired
    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping
    public R loadFile(MultipartFile file) {
        Map<String, Object> map = ossService.uploadFile(file);
        return R.ok(map);
    }
}
