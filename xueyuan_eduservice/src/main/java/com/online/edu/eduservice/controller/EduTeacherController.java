package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-05
 */
@Api(description="教师模块")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin //解决跨域问题
public class EduTeacherController {

    //注入service
    @Autowired
    EduTeacherService eduTeacherService;

    //1、查询所有讲师的功能
    @ApiOperation(value = "查询所有讲师的功能")
    @GetMapping
    public R getAllTeacherList(){
        //调用service的方法
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //2、逻辑删除
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")   //路径传值
    public R deleteTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean b = eduTeacherService.deleteTeacherById(id);
        if(b){
            return R.ok();
        }
        return R.error();
    }

    //3、分页查询讲师列表的方法
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,@PathVariable Long limit){
        //创建pageTeacher对象，传递两个参数，一个是当前页，一个是每页的大小
        Page<EduTeacher> pageTeacher=new Page<>(page,limit);
        //调用分页查询方法
        IPage<EduTeacher> page1 = eduTeacherService.page(pageTeacher, null);
        //从page1对象中获取分页数据
        List<EduTeacher> records = page1.getRecords();
        long total = page1.getTotal();
        return R.ok().data("Total",total).data("items",records);
    }

    //4、条件查询带分页
    @ApiOperation(value = "分页讲师模糊查询列表")
    @PostMapping("moreConditionPageList/{page}/{limit}")
    public R getMoreConditionPageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) QueryTeacher queryTeacher){
        Page<EduTeacher> pageList=new Page<>(page,limit);
        //调用service的方法实现条件查询带分页
        eduTeacherService.pageListCondition(pageList,queryTeacher);
        //从page1对象中获取分页数据
        List<EduTeacher> records = pageList.getRecords();
        long total = pageList.getTotal();
        return R.ok().data("total",total).data("items",records);
    }

    //5、添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }
        return R.error();
    }

    //6、根据id查询讲师信息
    @ApiOperation(value ="根据id查询讲师信息")
    @GetMapping("queryById/{id}")
    public R queryById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    //7、根据id修改讲师信息
    @ApiOperation(value = "根据id修改讲师信息")
    @PostMapping("updateById/{id}")
    public R updateById(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //8、按日期查询
    @ApiOperation(value ="按照添加日期查询")
    @PostMapping("queryByDate")
    public R queryByDate(){
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.orderByDesc("gmt_create","gmt_modified");
        List<EduTeacher> list = eduTeacherService.list(wrapper);
        return R.ok().data("items",list);
    }


}

