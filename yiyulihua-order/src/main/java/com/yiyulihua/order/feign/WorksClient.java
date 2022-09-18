package com.yiyulihua.order.feign;

import com.yiyulihua.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author sunbo
 * @since 2022/09/18 19:14
 */
@FeignClient("yiyulihua-works")
public interface WorksClient {
    @PutMapping("/update_bid_num/{id}")
    public Result<?> updateNumber(@PathVariable("id") String workId);
}
