package com.online.edu.eduservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient("xueyuan-vidservice") //上注册中心找vidservice服务
@Component
public interface VodClient {

    @DeleteMapping("/vidservice/video/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId);

    //定义调用删除多个视频的方法
    @DeleteMapping("/vidservice/video/deleteMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoList") List videoList);
}
