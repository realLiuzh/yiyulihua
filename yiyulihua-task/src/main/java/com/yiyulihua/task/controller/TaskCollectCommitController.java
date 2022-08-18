package com.yiyulihua.task.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.TaskCommitTo;
import com.yiyulihua.common.to.UserCollectTaskTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.TaskListVo;
import com.yiyulihua.task.service.TaskCollectCommitService;
import com.yiyulihua.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "TaskCollectCommitController", tags = {"收藏和参与任务"})
@RestController
public class TaskCollectCommitController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskCollectCommitService taskCollectCommitService;


    @ApiOperation(value = "用户收藏任务", notes = "携带token")
    @PostMapping("/collect")
    public Result<Object> collectTask(@RequestBody UserCollectTaskTo userCollectTaskTo) {
        taskService.collectTask(userCollectTaskTo.getTaskId(), 1);
        return Result.success();
    }

    @ApiOperation(value = "用户取消收藏任务", notes = "携带token")
    @PostMapping("/uncollect")
    public Result<Object> unCollectTask(@RequestBody UserCollectTaskTo userCollectTaskTo) {
        taskService.collectTask(userCollectTaskTo.getTaskId(), 0);
        return Result.success();
    }


    @ApiOperation(value = "查询用户收藏的任务", notes = "携带token")
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
    @GetMapping("/collected/{current}/{size}")
    public Result<PageUtils<TaskListVo>> getCollect(@PathVariable("current") Integer current,
                                                    @PathVariable("size") Integer size) {
        PageUtils<TaskListVo> page = taskService.getCollect(current, size);

        return new Result<PageUtils<TaskListVo>>().setData(page);
    }

    @ApiOperation(value = "查询用户参与的任务", notes = "携带token")
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
    @GetMapping("/committed/{current}/{size}")
    public Result<PageUtils<TaskListVo>> getJoin(@PathVariable("current") Integer current,
                                                 @PathVariable("size") Integer size) {
        PageUtils<TaskListVo> page = taskService.getJoin(current, size);

        return new Result<PageUtils<TaskListVo>>().setData(page);
    }


    @ApiOperation(value = "用户提交任务", notes = "携带token")
    @PostMapping("/commit")
    public Result<Object> join(@RequestBody TaskCommitTo taskCommitTo) {
        taskCollectCommitService.commit(taskCommitTo);
        return Result.success();
    }


}
