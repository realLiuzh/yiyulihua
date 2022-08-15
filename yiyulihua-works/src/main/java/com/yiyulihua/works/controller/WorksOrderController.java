package com.yiyulihua.works.controller;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.OrderTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.ParticipantWorksVo;
import com.yiyulihua.works.service.WorksOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-08-14
 */
@Api(value = "作品订单管理", tags = "作品订单管理")
@RestController
@RequestMapping("/worksOrder")
public class WorksOrderController {

    private final WorksOrderService worksOrderService;

    @Autowired
    public WorksOrderController(WorksOrderService worksOrderService) {
        this.worksOrderService = worksOrderService;
    }


    @ApiOperation(value = "通过用户 id 分页查询用户参与的作品", notes = "通过 token 获取用户id")
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
    public Result<PageUtils<ParticipantWorksVo>> getWorksOrderByUserId(
            @PathVariable("current") Integer current,
            @PathVariable("size") Integer size) {
        PageUtils<ParticipantWorksVo> page = worksOrderService.getWorksOrderByUserId(current, size);

        return new Result<PageUtils<ParticipantWorksVo>>().setData(page);
    }

    /**
     * 生成订单,待支付成功后自动更新支付状态为已支付,支付后向卖家发送消息,待卖家确认后自动更改订单状态
     */
    @ApiOperation(value = "创建订单,返回订单号", notes = "生成订单,待支付成功后自动更新支付状态为已支付,支付后向卖家发送消息,待卖家确认后自动更改订单状态")
    @PostMapping
    public Result<Map<String, String>> createOrder(@RequestBody OrderTo order) {
        String orderNo = worksOrderService.createOrder(order);

        Map<String, String> map = new HashMap<>();
        map.put("orderNo", orderNo);
        return new Result<Map<String, String>>().setData(map);
    }

    @ApiOperation(value = "根据 id 删除作品订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",
                    value = "订单 id",
                    required = true,
                    paramType = "path"
            )
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        worksOrderService.removeById(id);

        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "根据 id 批量删除订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",
                    value = "订单 id 数组",
                    required = true,
                    paramType = "body",
                    dataType = "String[]")
    })
    @PostMapping("/delete")
    public Result delete(@RequestBody String[] ids) {
        worksOrderService.removeByIds(Arrays.asList(ids));

        return Result.success();
    }

}
