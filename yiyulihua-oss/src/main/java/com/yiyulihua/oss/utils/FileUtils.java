package com.yiyulihua.oss.utils;


import java.io.*;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class FileUtils {

    /**
     * description:
     *
     * @param multiFile 文件
     * @return {@link File}
     * @author sunbo
     * @date 2022/7/20 15:48
     */
    public static File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        try {
            File file = File.createTempFile(fileName, suffix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
