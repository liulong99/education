package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.EduAllCourseDto;
import com.online.edu.eduservice.entity.query.QueryCourse;
import com.online.edu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-09
 */
@Api(description="课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "根据id查询课程基本信息")
    @GetMapping("getCourseById/{id}")
    public R getCourseById(@PathVariable String id){
        CourseInfoForm courseInfoForm=eduCourseService.getCourseById(id);
        if(courseInfoForm==null){
            return R.error().message("查询课程基本信息失败");
        }
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    @ApiOperation(value = "新增课程")
    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
         String  courseId=eduCourseService.saveCourseInfo(courseInfoForm);
         if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId",courseId);
         }else{
             return R.error().message("保存失败");
         }
    }

    @ApiOperation(value = "根据课程id修改")
    @PostMapping("updateCourseById")
    public R updateCourseById(@RequestBody CourseInfoForm courseInfoForm){
        boolean b= eduCourseService.updateCourseById(courseInfoForm);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "按添加和修改时间查询所有课")
    @RequestMapping
    public R QueryAllDesc(){
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create","gmt_modified");
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("items",list);
    }

    @ApiOperation(value="分页课表列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(@ApiParam(name = "page",value = "当前页码",required = true) @PathVariable Long page,
                       @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable Long limit,
                       @ApiParam(name = "queryCourse",value = "查询对象",required = false) QueryCourse queryCourse){
        Page<EduCourse> pageParam=new Page<>(page,limit);
        eduCourseService.pageQuery(pageParam,queryCourse);
        List<EduCourse> lists=pageParam.getRecords();
        long total=pageParam.getTotal();
        return R.ok().data("total",total).data("rows",lists);
    }

    @ApiOperation(value="根据id删除课程信息")
    @DeleteMapping("deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id){
        boolean b= eduCourseService.deleteCourseById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value="根据课程id查询课程所有信息")
    @GetMapping("getAllCourseInfo/{id}")
    public R getAllCourseInfo(@PathVariable String id){
        EduAllCourseDto eduAllCourseDto =eduCourseService.getAllCourseInfo(id);
        return R.ok().data("items",eduAllCourseDto);
    }

    @ApiOperation(value="根据课程id修改课程状态")
    @GetMapping("changeCourseStatus/{id}")
    public R changeCourseStatus(@PathVariable String id){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        boolean b = eduCourseService.updateById(eduCourse);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

