package com.yiyulihua.oss.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.vo.TypeListVo;
import com.yiyulihua.oss.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-07-25
 */
@Api(value = "文件类型管理", tags = "作品类型管理")
@RestController
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @ApiOperation(value = "获取所有文件类型及其格式")
    @GetMapping
    public Result<List<TypeListVo>> list() {
        List<TypeListVo> list = typeService.getTypeList();
        return new Result<List<TypeListVo>>().setData(list);
    }

}
