package com.online.vod.vidservice.controller;

import com.online.edu.common.R;
import com.online.vod.vidservice.service.VideoAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        videoAdminService.deleteVideo(id);
        return R.ok().message("删除成功");
    }
}
