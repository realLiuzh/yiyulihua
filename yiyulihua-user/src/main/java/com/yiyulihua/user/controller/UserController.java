package com.yiyulihua.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.dev33.satoken.stp.StpUtil;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.UserLoginTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;
import com.yiyulihua.common.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.common.po.UserEntity;
import com.yiyulihua.user.service.UserService;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @author Ö¾ê»µÄÁõ
 * @email 1138423425@qq.com
 * @date 2022-07-16 18:12:48
 */
@RestController
@Api(value = "UserController", tags = {"用户访问接口"})
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @ApiIgnore
    @RequestMapping("/list")
    //@RequiresPermissions("user:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 获取特定用户的信息 供远程调用
     * TODO bug:data属性序列化失败
     */
    @ApiIgnore
    @GetMapping("/info/{id}")
    @ResponseBody
    public Result<UserVo> info(@PathVariable("id") Integer id) {
        UserVo user = userService.getById(id);
        return new Result<UserVo>().setData(user);
    }


    @ApiOperation(value = "根据token获取用户信息", notes = "需在已登录模式下调用，即header中带有token")
    @GetMapping("/info")
    @ResponseBody
    public Result<UserVo> infoByToken() {
        UserLoginTo userInfo = (UserLoginTo) StpUtil.getSession().get("userInfo");
        UserVo user = userService.getById(userInfo.getId());
        return new Result<UserVo>().setData(user);
    }


    /**
     * 保存
     */
    @ApiIgnore
    @RequestMapping("/save")
    //@RequiresPermissions("user:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiIgnore
    @RequestMapping("/update")
    //@RequiresPermissions("user:user:update")
    public R update(@RequestBody UserEntity user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiIgnore
    @RequestMapping("/delete")
    //@RequiresPermissions("user:user:delete")
    public R delete(@RequestBody Integer[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * feign 远程调用
     */
    @ApiIgnore
    @GetMapping("/allId")
    public List<Integer> getAllUserId() {
        return userService.getAllUserId();
    }

}
