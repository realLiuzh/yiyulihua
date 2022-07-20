package com.yiyulihua.works.controller;

import java.util.Arrays;


import com.yiyulihua.common.po.WorksEntity;
import com.yiyulihua.common.query.PageQuery;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.works.service.WorksService;


/**
 * @author snbo
 * @date 2022-07-16 16:53:42
 */

@RestController
@RequestMapping("/works")
public class WorksController {
    private final WorksService worksService;

    @Autowired
    public WorksController(WorksService worksService) {
        this.worksService = worksService;
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody PageQuery params) {
        PageUtils page = worksService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") String id) {
        WorksEntity work = worksService.getById(id);

        return R.ok().put("work", work);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody WorksEntity work) {
        worksService.save(work);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody WorksEntity work) {
        worksService.updateById(work);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("{/id}")
    public R delete(@PathVariable String id){
        worksService.removeById(id);

        return R.ok();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        worksService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
