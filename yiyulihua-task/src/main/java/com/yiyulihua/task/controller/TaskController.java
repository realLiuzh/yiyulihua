package com.yiyulihua.task.controller;

import java.util.Arrays;
import java.util.List;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.yiyulihua.common.annotation.I18n;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.TaskBuildTo;
import com.yiyulihua.common.vo.TaskListVo;
import com.yiyulihua.common.vo.TaskMyPublishVo;
import com.yiyulihua.common.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyulihua.common.po.TaskEntity;
import com.yiyulihua.task.service.TaskService;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 任务Controller
 *
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@Api(value = "TaskController", tags = {"任务访问接口"})
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;


    @ApiOperation("查询任务集市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",
                    value = "页数(默认为1",
                    required = false,
                    paramType = "query"),
            @ApiImplicitParam(name = "limit",
                    value = "每页条数(默认为8",
                    required = false,
                    paramType = "query")
    })
    @I18n
    @GetMapping("/list")
    public Result<PageUtils<TaskListVo>> list(@RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "limit", required = false) Integer limit) {

        PageUtils<TaskListVo> pageUtils = taskService.queryPage(new PageQuery(page, limit));
        return new Result<PageUtils<TaskListVo>>().setData(pageUtils);
    }


    @ApiOperation("获取任务的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "任务id",
                    required = true,
                    paramType = "query"),
    })
    @I18n
    @GetMapping("/info")
    public Result<TaskVo> selectById(@RequestParam("id") Integer id) {
        TaskVo task = taskService.selectById(id);
        return new Result<TaskVo>().setData(task);
    }

    @ApiOperation(value = "通过用户 id 分页查询用户发布的任务", notes = "根据 token 获取用户id")
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
    public Result<PageUtils<TaskMyPublishVo>> getTaskByPublisherId(
            @PathVariable("current") Integer current,
            @PathVariable("size") Integer size) {
        PageUtils<TaskMyPublishVo> page = taskService.getByPublisherId(current, size);

        return new Result<PageUtils<TaskMyPublishVo>>().setData(page);
    }


    @ApiOperation("任务发布")
    @PostMapping("/build")
    public Result<Object> buildTask(@RequestBody TaskBuildTo taskBuildTo) {
        taskService.buildTask(taskBuildTo);
        return Result.success();
    }

    @ApiOperation("发布推荐")
    @GetMapping("/recommend")
    public Result<List<TaskListVo>> recommendTask() {
        List<TaskListVo> list = taskService.recommendTask();
        return new Result<List<TaskListVo>>().setData(list);
    }


    /**
     * 保存
     */
    @ApiIgnore
    @RequestMapping("/save")
    //@RequiresPermissions("task:task:save")
    public R save(@RequestBody TaskEntity task) {
        taskService.save(task);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiIgnore
    @RequestMapping("/update")
    //@RequiresPermissions("task:task:update")
    public R update(@RequestBody TaskEntity task) {
        taskService.updateById(task);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiIgnore
    @RequestMapping("/delete")
    //@RequiresPermissions("task:task:delete")
    public R delete(@RequestBody Integer[] ids) {
        taskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
