package com.yiyulihua.task.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yiyulihua.task.entity.TaskEntity;
import com.yiyulihua.task.service.TaskService;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;



/**
 * 
 *
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-15 21:01:57
 */
@RestController
@RequestMapping("task/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("task:task:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = taskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("task:task:info")
    public R info(@PathVariable("id") Integer id){
		TaskEntity task = taskService.getById(id);

        return R.ok().put("task", task);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("task:task:save")
    public R save(@RequestBody TaskEntity task){
		taskService.save(task);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("task:task:update")
    public R update(@RequestBody TaskEntity task){
		taskService.updateById(task);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("task:task:delete")
    public R delete(@RequestBody Integer[] ids){
		taskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
