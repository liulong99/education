package com.online.edu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.query.EduChapterDto;
import com.online.edu.eduservice.entity.query.TeacherAllInfoDto;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("前端显示课程的方法")
@RestController
@RequestMapping("/eduservice/frontCourse")
@CrossOrigin
public class FrontEduCourseController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;

    //前端课程分页列表的方法
    @ApiOperation(value = "前端课程分页列表的方法")
    @GetMapping("{page}/{limit}")
    public R getCourseListPage(@PathVariable Long page,@PathVariable Long limit){
        Page<EduCourse>eduCoursePage=new Page<>(page,limit);
         Map<String,Object>map=eduCourseService.getCourseListPage(eduCoursePage);
        return R.ok().data(map);
    }

    //前端根据课程id插叙你课程详细信息
    @ApiOperation(value = "前端根据课程id插叙你课程详细信息")
    @GetMapping("{id}")
    public R getCourseInfoAll(@PathVariable String id){
        //根据课程id查询信息，包含课程的基本信息，讲师信息，分类信息
        TeacherAllInfoDto courseInfo= eduCourseService.getTeacherAllInfo(id);
        //根据课程id查询课程里面好友的章节。章节里面的小节
        List<EduChapterDto> chapterVideoList = eduChapterService.getChapterByCourseId(id);
        return R.ok().data("courseInfo",courseInfo).data("chapterVideoList",chapterVideoList);
    }

}
