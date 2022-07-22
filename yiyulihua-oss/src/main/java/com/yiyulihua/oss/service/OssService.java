package com.yiyulihua.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    /**
     * description: 根据文件url从oss中删除文件
     *
     * @param url 文件url
     * @return {@link boolean}
     * @author sunbo
     * @date 2022/7/22 19:40
     */
    boolean removeFile(String url);

    /**
     * description: 根据url批量删除文件
     *
     * @param urls url 数组
     * @return {@link List<String>}
     * @author sunbo
     * @date 2022/7/22 21:06
     */
    List<String> removedFiles(List<String> urls);
}
