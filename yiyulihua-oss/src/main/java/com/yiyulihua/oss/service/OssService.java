package com.yiyulihua.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author sunbo
 * @date 2022-07-18-18:16
 */

@Service
public interface OssService {

    /**
     * description: 上传作品文件到阿里云 oss 平台
     *
     * @param file 文件
     * @return {@link String} 上传成功后返回图片 url
     * @author sunbo
     * @date 2022/7/18 18:26
     */
    Map<String, Object> uploadFile(MultipartFile file);
}
