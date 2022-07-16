package com.yiyulihua.task.feign;


import com.yiyulihua.common.utils.R;
import com.yiyulihua.common.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("yiyulihua-user")
public interface UserFeignService {

    @GetMapping("/user/info/{id}")
    R<UserVo> info(@PathVariable("id") Integer id);
}
