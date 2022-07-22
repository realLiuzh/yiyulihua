package com.yiyulihua.oss.controller;

import com.yiyulihua.common.utils.R;
import com.yiyulihua.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    /**
     * description: 上传文件
     *
     * @param file 图片/文本/音频
     * @return {@link R}
     * @author sunbo
     * @date 2022/7/22 21:08
     */
    @PostMapping
    public R loadFile(MultipartFile file) {
        Map<String, Object> map = ossService.uploadFile(file);
        return R.ok(map);
    }

    /**
     * description: 删除文件
     *
     * @param url 文件url
     * @return {@link R}
     * @author sunbo
     * @date 2022/7/22 21:08
     */
    @DeleteMapping
    public R deleteFile(@PathVariable("url") String url) {
        if (ossService.removeFile(url)) {
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * description: 批量删除文件
     *
     * @param urls 文件url列表
     * @return {@link R}
     * @author sunbo
     * @date 2022/7/22 21:09
     */
    @DeleteMapping("/remove")
    public R deleteFiles(@RequestParam("urls") List<String> urls) {
        List<String> list = ossService.removedFiles(urls);
        if (list == null) {
            return R.error("删除失败");
        }
        return R.ok("删除成功");
    }
}
