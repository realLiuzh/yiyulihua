package com.yiyulihua.user.controller;


import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yiyulihua.user.service.MessageService;


/**
 * @author snbo
 * @date 2022-07-30 10:38:30
 */
@RestController
@RequestMapping
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        MessageEntity message = messageService.getById(id);

        return R.ok().put("message", message);
    }

}
