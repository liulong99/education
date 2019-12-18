package com.online.edu.staservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("xueyuan-ucservice")
public interface UcClient {
    @GetMapping("/ucservice/member/registerNum/{day}")
    public R registerNum(@PathVariable("day") String day);
}
