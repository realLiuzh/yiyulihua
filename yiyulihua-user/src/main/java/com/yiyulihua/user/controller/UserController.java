package com.yiyulihua.user.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.dev33.satoken.stp.StpUtil;
import com.yiyulihua.common.to.UserLoginTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;
import com.yiyulihua.common.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.common.po.UserEntity;
import com.yiyulihua.user.service.UserService;


/**
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-16 18:12:48
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 获取特定用户的信息
     * TODO bug:data属性序列化失败
     */
    @GetMapping("/info/{id}")
    @ResponseBody
    public R<UserVo> info(@PathVariable("id") Integer id) {
        UserVo user = userService.getById(id);
        R<UserVo> r = new R<>();
        r.put("data", user);
//        r.setData(user);
//        System.out.println(r.getData());
        return r;
    }


    /**
     * 获取特定用户的信息
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public R infoByToken() {
        UserLoginTo userInfo = (UserLoginTo) StpUtil.getSession().get("userInfo");
        UserVo user = userService.getById(userInfo.getId());
        return R.ok().put("data", user);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:user:update")
    public R update(@RequestBody UserEntity user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:user:delete")
    public R delete(@RequestBody Integer[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
