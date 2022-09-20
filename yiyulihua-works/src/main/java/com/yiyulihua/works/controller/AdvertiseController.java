package com.yiyulihua.works.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.AdvertiseTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.ValidatedGroup;
import com.yiyulihua.common.vo.AdvertiseVo;
import com.yiyulihua.works.service.AdvertiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-08-13
 */
@Api(value = "广告管理", tags = "后台管理")
@RestController
@RequestMapping("/admin")
@Validated
public class AdvertiseController {

    private final AdvertiseService advertiseService;

    @Autowired
    public AdvertiseController(AdvertiseService advertiseService) {
        this.advertiseService = advertiseService;
    }

    @ApiOperation(value = "分页查询广告信息,根据权重值 sort 排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",
                    value = "当前页",
                    required = true,
                    paramType = "path"),
            @ApiImplicitParam(name = "size",
                    value = "每页记录条数",
                    required = true,
                    paramType = "path"),
            @ApiImplicitParam(name = "position",
                    value = "广告位置(0:首页, 1:底部)",
                    required = true,
                    paramType = "query")
    })
    @GetMapping("/{current}/{size}")
    public Result<PageUtils<AdvertiseVo>> list(
            @PathVariable("current") Integer current,
            @PathVariable("size") Integer size,
            @RequestParam("position") Integer position) {
        PageUtils<AdvertiseVo> page = advertiseService.getListPage(current, size, position);

        return new Result<PageUtils<AdvertiseVo>>().setData(page);
    }


    @ApiOperation(value = "添加广告信息", notes = "添加操作无需填写 id 值")
    @PostMapping
    public Result<?> save(@RequestBody @Validated AdvertiseTo advertise) {
        advertiseService.saveAdvertise(advertise);

        return Result.success();
    }

    @ApiOperation(value = "根据 id 修改广告信息")
    @PutMapping
    public Result<?> update(@RequestBody
                            @Validated({ValidatedGroup.Update.class, Default.class})
                            AdvertiseTo advertise) {
        advertiseService.updateAdvertise(advertise);

        return Result.success();
    }


    @ApiOperation(value = "根据 id 删除广告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "广告 id",
                    required = true,
                    paramType = "path"
            )
    })
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Integer id) {
        advertiseService.removeById(id);

        return Result.success();
    }

    @ApiOperation(value = "根据广告 id 批量删除广告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",
                    value = "广告 id 数组",
                    required = true,
                    paramType = "body",
                    dataType = "String[]")
    })
    @PostMapping("/delete")
    public Result<?> deleteBatch(@RequestBody Integer[] ids) {
        advertiseService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }
}