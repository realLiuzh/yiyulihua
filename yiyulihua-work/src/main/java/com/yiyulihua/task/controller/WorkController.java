package com.yiyulihua.task.controller;

import java.util.Arrays;


import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.task.entity.WorkEntity;
import com.yiyulihua.task.service.WorkService;


/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */

@RestController
@RequestMapping("/work")
public class WorkController {
    private final WorkService workService;

    @Autowired
    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody PageQuery params) {
        PageUtils page = workService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") String id) {
        WorkEntity work = workService.getById(id);

        return R.ok().put("work", work);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody WorkEntity work) {
        workService.save(work);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody WorkEntity work) {
        workService.updateById(work);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("{/id}")
    public R delete(@PathVariable String id){
        workService.removeById(id);

        return R.ok();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        workService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
