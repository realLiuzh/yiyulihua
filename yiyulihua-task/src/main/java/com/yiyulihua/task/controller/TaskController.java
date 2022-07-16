package com.yiyulihua.task.controller;

import java.util.Arrays;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.common.po.TaskEntity;
import com.yiyulihua.task.service.TaskService;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;


/**
 * 任务Controller
 *
 * @author lzh
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 查询任务集市列表
     */
    @GetMapping("/list")
    public R list(PageQuery param) {
        PageUtils page = taskService.queryPage(param);

        return R.ok().put("page", page);
    }


    /**
     * 获取任务的详细信息
     */
    @RequestMapping("/info/{id}")
    public R selectByid(@PathVariable("id") Integer id) {
        TaskVo task = taskService.selectByid(id);
        return R.ok().put("data", task);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("task:task:save")
    public R save(@RequestBody TaskEntity task) {
        taskService.save(task);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("task:task:update")
    public R update(@RequestBody TaskEntity task) {
        taskService.updateById(task);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("task:task:delete")
    public R delete(@RequestBody Integer[] ids) {
        taskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
