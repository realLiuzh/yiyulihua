package com.yiyulihua.websocket.feign;

import com.yiyulihua.common.po.MessageEntity;
import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.vo.ResultMessageVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunbo
 * @since  2022/08/03 12:02
 */

@Component
@FeignClient(value = "yiyulihua-user")
public interface MessageAndUserFeignClient {
    @GetMapping("/remote/{receiveUserId}")
    List<ResultMessageVo> getOfflineMessage(
            @PathVariable("receiveUserId") Integer receiveUserId);

    @PutMapping("/remoteUpdate/{receiveUserId}")
    Result<?> updateOfflineMessageStatus(
            @PathVariable("receiveUserId") Integer receiveUserId);

    @PostMapping("/remoteSave")
    Integer save(@RequestBody MessageEntity message);

    @GetMapping("/allId")
    List<Integer> getAllUserId();

}