package com.yiyulihua.user.controller;


import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.utils.R;
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
@Api("消息管理")
@RestController
@RequestMapping
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @ApiOperation("根据用户 id 分页获取聊天记录")
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
    public R info(@PathVariable("current") Long current,
                  @PathVariable Long size,
                  @RequestParam("Id") String id) {
//        PageUtils<> page = messageService.getHistoryMessage();

        return null;
    }

}
