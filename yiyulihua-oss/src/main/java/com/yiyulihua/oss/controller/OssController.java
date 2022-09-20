package com.yiyulihua.oss.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.oss.service.OssService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author sunbo
 * @date 2022-07-18-17:45
 */
@Api(value = "作品文件的上传删除等", tags = "作品文件管理")
@RestController
public class OssController {

    private final OssService ossService;

    @Autowired
    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",
                    value = "作品文件",
                    required = true,
                    dataType = "MultipartFile",
                    allowMultiple = true,
                    paramType = "query")
    })
    @PostMapping
    public Result<Map<String, Object>> loadFile(
            @RequestPart
            @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = ossService.uploadFile(file);

        return new Result<Map<String, Object>>().setData(map);
    }


    @ApiOperation(value = "根据 url 删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url",
                    value = "文件url",
                    required = true,
                    paramType = "query")
    })
    @DeleteMapping
    public Result<?> deleteFile(@RequestParam("url") String url) {
        if (ossService.removeFile(url)) {
            return Result.success();
        }
        return Result.error("删除失败");
    }


    @ApiOperation(value = "根据 id 批量删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "urls",
                    value = "url 数组",
                    required = true,
                    paramType = "body")})
    @PostMapping("/delete")
    public Result deleteFiles(@RequestBody String[] urls) {
        List<String> list = ossService.removedFiles(Arrays.asList(urls));
        if (list == null) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
