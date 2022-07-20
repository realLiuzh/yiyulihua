package com.yiyulihua.works.feign;

import com.yiyulihua.common.utils.R;
import com.yiyulihua.common.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sunbo
 * @date 2022-07-18-12:04
 */

@FeignClient("yiyulihua-user")
public interface UserClient {
    @GetMapping("/user/info/{id}")
    R<UserVo> info(@PathVariable("id") Integer id);
}
