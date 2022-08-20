package com.yiyulihua.user.controller;


import com.yiyulihua.common.po.MessageEntity;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * @author snbo
 * @date 2022/07/30 10:38:30
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
                    paramType = "path")
    })
    @GetMapping("/{current}/{size}")
    public Result<PageUtils<ResultMessageVo>> info(@PathVariable("current") Integer current,
                                                   @PathVariable("size") Integer size) {
        PageUtils<ResultMessageVo> page = messageService.getHistoryMessagePage(current, size);

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

    @ApiOperation(value = "根据用户 id 单方面删除两个用户间的所有聊天记录", notes = "本用户 id 从 token 获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toUserId",
                    value = "对方id",
                    dataType = "String",
                    required = true,
                    paramType = "body")
    })
    @PutMapping("matchUser")
    public Result removeAllMsgByUserId(@RequestBody String toUserId) {
        messageService.deleteRecordsBetweenUser(toUserId);

        return Result.success();
    }

    @ApiIgnore
    @GetMapping("/remote/{receiveUserId}")
    public List<ResultMessageVo> getOfflineMessage(@PathVariable("receiveUserId") String receiveUserId) {
        return messageService.getOfflineMessage(receiveUserId);
    }

    @ApiIgnore
    @PutMapping("/remoteUpdate/{receiveUserId}")
    public Result updateOfflineMessageStatus(@PathVariable("receiveUserId") String receiveUserId) {
        messageService.updateOfflineStatus(receiveUserId);

        return Result.success();
    }

    @ApiIgnore
    @PostMapping("/remoteSave")
    public String save(@RequestBody MessageEntity message) {
        messageService.save(message);

        return message.getId();
    }
}
