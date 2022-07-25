package com.yiyulihua.works.controller;

import java.util.Arrays;


import com.yiyulihua.common.query.WorksQuery;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.WorksDetailsVo;
import com.yiyulihua.common.vo.WorksListVo;
import com.yiyulihua.common.vo.WorksPublishVo;
import com.yiyulihua.common.vo.WorksUpdateVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.works.service.WorksService;


/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */

@Api(value = "作品管理")
@RestController
public class WorksController {
    private final WorksService worksService;

    @Autowired
    public WorksController(WorksService worksService) {
        this.worksService = worksService;
    }

    @ApiOperation(value = "按条件分页查询作品信息", notes = "timeSort priceSort为排序参数,大于0为降序,小于0为升序,默认为0不排序.如果要同时按时间和价格排序,比较两者绝对值,绝对值大的先排序", tags = "查询操作")
    @PostMapping("/list")
    public Result<PageUtils<WorksListVo>> list(@RequestBody(required = true) WorksQuery worksQuery) {
        PageUtils<WorksListVo> page = worksService.queryPage(worksQuery);

        return new Result<PageUtils<WorksListVo>>().setData(page);
    }


    @ApiOperation(value = "根据作品id查询作品的详细信息", tags = "查询操作")
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


    @ApiOperation(value = "发布或保存作品", notes = "worksStatus 为作品状态,保存设为0,发布设为1; ", tags = "增改操作")
    @PostMapping
    public Result save(@RequestBody(required = true) WorksPublishVo work) {
        worksService.publishOrSave(work);

        return Result.success();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "根据作品id更新作品信息", notes = "id 为必填项,其余非必需", tags = "增改操作")
    @PutMapping
    public Result update(@RequestBody(required = true) WorksUpdateVo work) {
        worksService.updateInfoById(work);

        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "根据 id 删除作品信息", tags = "删除操作")
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
    @ApiOperation(value = "根据 id 批量删除作品信息", tags = "删除操作")
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
}