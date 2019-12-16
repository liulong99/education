package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.query.EduChapterDto;
import com.online.edu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-11
 */
@Api(description="章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        boolean b=eduChapterService.deleteById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value="修改章节的方法")
    @PostMapping("updateById")
    public R updateById(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateById(eduChapter);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("queryById/{id}")
    public R queryById(@PathVariable String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        if(eduChapter!=null){
            return R.ok().data("eduChapter",eduChapter);
        }else{
            return R.error();
        }
    }

    @ApiOperation(value="增加章节的方法")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据课程id查询章节和小节")
    @GetMapping("getChapterByCourseId/{id}")
    public R getChapterByCourseId(@PathVariable String id){
        List<EduChapterDto> lists=eduChapterService.getChapterByCourseId(id);
        return R.ok().data("items",lists);
    }

}

