package com.yiyulihua.user.controller;


import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.MessageDeleteTo;
import com.yiyulihua.common.to.MessageDeleteUserTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.ResultMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yiyulihua.user.service.MessageService;


/**
 * @author snbo
 * @date 2022-07-30 10:38:30
 */
@Api(value = "消息管理", tags = "用户聊天消息管理")
@RestController
@RequestMapping
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @ApiOperation("根据用户 id 分页获取最近发消息聊天记录(每个人仅获取最近一条)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",
                    value = "当前页",
                    readOnly = true,
                    type = "path"),
            @ApiImplicitParam(name = "size",
                    value = "每页记录条数",
                    required = true,
                    paramType = "path"),
            @ApiImplicitParam(name = "Id",
                    value = "用户 id",
                    required = true,
                    paramType = "query")
    })
    @GetMapping("/{current}/{size}")
    public Result<PageUtils<ResultMessageVo>> info(@PathVariable("current") Integer current,
                                                   @PathVariable("size") Integer size,
                                                   @RequestParam("Id") String id) {
        PageUtils<ResultMessageVo> page = messageService.getHistoryMessagePage(current, size, id);

        return new Result<PageUtils<ResultMessageVo>>().setData(page);
    }

    @ApiOperation(value = "根据 id 单方面删除聊天记录")
    @PutMapping("one")
    public Result removeMsgRecord(@RequestBody MessageDeleteTo deleteVo) {
        messageService.deleteRecords(deleteVo);

        return Result.success();
    }

    @ApiOperation(value = "根据 id 单方面批量删除聊天记录")
    @PutMapping("batch")
    public Result removeMsgRecords(@RequestBody MessageDeleteTo[] deleteVos) {
        messageService.deleteRecordsBatch(deleteVos);

        return Result.success();
    }

    @ApiOperation(value = "根据用户 id 单方面删除两个用户间的所有聊天记录")
    @PutMapping("matchUser")
    public Result removeAllMsgByUserId(@RequestBody MessageDeleteUserTo deleteUserTo) {
        messageService.deleteRecordsBetweenUser(deleteUserTo);

        return Result.success();
    }
}
