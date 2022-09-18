package com.yiyulihua.order.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.AdvertiseOrderTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.AdvertiseOrderVo;
import com.yiyulihua.order.service.AdvertiseOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-08-24
 */

@Api(value = "广告订单管理", tags = "广告订单管理")
@RestController
@RequestMapping("/ad_order")
@Validated
public class AdvertiseOrderController {

    private final AdvertiseOrderService advertiseOrderService;

    @Autowired
    public AdvertiseOrderController(AdvertiseOrderService advertiseOrderService) {
        this.advertiseOrderService = advertiseOrderService;
    }


    @ApiOperation("分页获取广告订单信息")
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
    @GetMapping("/{current}/{size}")
    public Result<PageUtils<AdvertiseOrderVo>> page(
            @PathVariable("current") @Min(value = 0,message = "页数不小于零") Integer current,
            @PathVariable("size") @Min(value = 0,message = "记录数不小于零") Integer size) {

        PageUtils<AdvertiseOrderVo> page = advertiseOrderService.pageInfo(current, size);

        return new Result<PageUtils<AdvertiseOrderVo>>().setData(page);
    }

    @ApiOperation("创建广告订单,返回订单号")
    @PostMapping
    public Result<Map<String, String>> createOrder(@RequestBody @Validated AdvertiseOrderTo advertiseOrderTo) {
        String orderNo = advertiseOrderService.createOrder(advertiseOrderTo);
        Map<String, String> map = new HashMap<>(1);
        map.put("orderNo", orderNo);

        return new Result<Map<String, String>>().setData(map);
    }

    @ApiOperation("根据广告订单 id 删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "广告订单 id",
                    required = true,
                    paramType = "path"
            )
    })
    @DeleteMapping("/{id}")
    public Result<?> deleteOrder(@PathVariable("id") @NotBlank @Length(min = 19, max = 19,message = "id 格式错误") String id) {
        advertiseOrderService.removeById(id);

        return Result.success();
    }


    @ApiOperation(value = "根据 id 批量删除广告订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",
                    value = "广告订单 id 数组",
                    required = true,
                    paramType = "body",
                    dataType = "String[]")
    })
    @PostMapping("/delete")
    public Result<?> delete(@RequestBody @NotEmpty String[] ids) {
        advertiseOrderService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
