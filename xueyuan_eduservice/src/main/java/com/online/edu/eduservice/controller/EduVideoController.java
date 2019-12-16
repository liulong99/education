package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-11
 */
@Api(description="小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation(value="新增小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value="修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean b = eduVideoService.updateById(eduVideo);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "查询小节")
    @GetMapping("queryVideo/{id}")
    public R queryVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    @ApiOperation(value="删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        boolean b=eduVideoService.deleteById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

