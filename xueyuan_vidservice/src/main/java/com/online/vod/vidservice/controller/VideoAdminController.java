package com.online.vod.vidservice.controller;

import com.online.edu.common.R;
import com.online.vod.vidservice.service.VideoAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vidservice/video")
@Api(description="阿里云视频点播微服务")
public class VideoAdminController {

    @Autowired
    private VideoAdminService videoAdminService;

    @ApiOperation(value = "阿里云上传视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(@RequestParam("file") MultipartFile file){
        String videoId=videoAdminService.uploadVideo(file);
        if (!StringUtils.isEmpty(videoId)) {
            return  R.ok().data("videoId",videoId);
        }else{
            return R.error().message("上传失败");
        }
    }

    @ApiOperation(value="删除阿里云视频")
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        videoAdminService.deleteVideo(videoId);
        return R.ok().message("删除成功");
    }

    //删除阿里云多个视频
    //参数是多个视频id的list集合
    @ApiOperation(value = "删除多个阿里云视频")
    @DeleteMapping("deleteMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoList") List videoList) {
        videoAdminService.deleteMoreVideo(videoList);
        return R.ok();
    }

    //根据视频id获取播放凭证
    @ApiOperation(value="根据视频id获取播放凭证")
    @GetMapping("getPalyAuth/{vid}")
    public R getPalyAuth(@PathVariable String vid){
        String playAuth=videoAdminService.getPalyAuth(vid);
        if(!StringUtils.isEmpty(playAuth)){
            return R.ok().data("playAuth",playAuth);
        }else{
            return R.error();
        }

    }
}
