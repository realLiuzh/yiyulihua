package com.yiyulihua.works.controller;

import java.util.Arrays;
import java.util.List;


import com.yiyulihua.common.query.WorksQuery;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.WorksPublishTo;
import com.yiyulihua.common.to.WorksUpdateTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.works.service.WorksService;


/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */

@Api(value = "作品管理", tags = "作品管理")
@RestController
public class WorksController {
    private final WorksService worksService;

    @Autowired
    public WorksController(WorksService worksService) {
        this.worksService = worksService;
    }

    @ApiOperation(value = "按条件分页查询作品信息", notes = "timeSort priceSort为排序参数,大于0为降序,小于0为升序,默认为0不排序.如果要同时按时间和价格排序,比较两者绝对值,绝对值大的先排序")
    @PostMapping("/list")
    public Result<PageUtils<WorksListVo>> list(@RequestBody(required = true) WorksQuery worksQuery) {
        PageUtils<WorksListVo> page = worksService.queryPage(worksQuery);

        return new Result<PageUtils<WorksListVo>>().setData(page);
    }


    @ApiOperation(value = "根据作品id查询作品的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "作品 id",
                    required = true,
                    paramType = "path")
    })
    @GetMapping("/{id}")
    public Result<WorksDetailsVo> info(@PathVariable("id") String id) {
        WorksDetailsVo works = worksService.getDetailsInfoById(id);

        return new Result<WorksDetailsVo>().setData(works);
    }


    @ApiOperation(value = "通过用户 id 分页查询用户发布的作品", notes = "根据 token 获取用户id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",
                    value = "当前页",
                    required = true,
                    paramType = "path"),
            @ApiImplicitParam(name = "size",
                    value = "每页记录条数",
                    required = true,
                    paramType = "path")
    })
    @GetMapping("/publisher/{current}/{size}")
    public Result<PageUtils<WorksMyPublishVo>> getWorksByPublisherId(
            @PathVariable("current") Integer current,
            @PathVariable("size") Integer size) {
        PageUtils<WorksMyPublishVo> works = worksService.getWorksByPublisherId(current, size);

        return new Result<PageUtils<WorksMyPublishVo>>().setData(works);
    }

    @ApiOperation(value = "发布或保存作品", notes = "worksStatus 为作品状态,保存设为0,发布设为1;")
    @PostMapping
    public Result save(@RequestBody(required = true) WorksPublishTo work) {
        worksService.publishOrSave(work);

        return Result.success();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "根据作品id更新作品信息", notes = "id 为必填项,其余非必需")
    @PutMapping
    public Result update(@RequestBody(required = true) WorksUpdateTo work) {
        worksService.updateInfoById(work);

        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "根据 id 删除作品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "作品 id",
                    required = true,
                    paramType = "path"
            )
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        worksService.removeById(id);

        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "根据 id 批量删除作品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",
                    value = "作品 id 数组",
                    required = true,
                    paramType = "body",
                    dataType = "String[]")
    })
    @PostMapping("/delete")
    public Result delete(@RequestBody String[] ids) {
        worksService.removeByIds(Arrays.asList(ids));

        return Result.success();

    }

    @ApiOperation("获取同类作品推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "作品id",
                    paramType = "path",
                    required = true)
    })
    @GetMapping("/recommend/{id}")
    public Result<List<WorksListVo>> getRecommend(@PathVariable("id") String id) {
        List<WorksListVo> list = worksService.getRecommend(id);

        return new Result<List<WorksListVo>>().setData(list);
    }
}