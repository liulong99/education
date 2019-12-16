package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.query.SubjectNestedVo;
import com.online.edu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-08
 */

@Api(description="课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin //跨域
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

    //1、查询一级二级subject内容
    @ApiOperation(value="嵌套数据列表")
    @GetMapping
    public R nestedList(){
        List<SubjectNestedVo> subjectNestedVoList=eduSubjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }

    //2、导入excel表格数据
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("import")
    public R importSubject(@RequestParam("file") MultipartFile file){
        List<String> msg = eduSubjectService.importSubject(file);
        if(msg.size() == 0){
            return R.ok().message("批量导入成功");
        }else{
            return R.error().message("部分数据导入失败").data("msgList", msg);
        }
    }

    //3、删除一级分类
    @ApiOperation(value = "删除一级分类")
    @DeleteMapping("{id}")
    public R deleteSubjectById(@PathVariable String id){
        boolean b = eduSubjectService.deleteSubjectById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //4、添加一级分类
    @ApiOperation(value = "添加一级分类")
    @PostMapping("addOneLevel")
    public R addOneLevel(@RequestBody EduSubject eduSubject){
        boolean b= eduSubjectService.addOneLevel(eduSubject);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //5、添加二级分类
    @ApiOperation(value = "添加二级分类")
    @PostMapping("addTwoLevel")
    public R addTwoLevel(@RequestBody EduSubject eduSubject){
        boolean b=eduSubjectService.addTwoLevel(eduSubject);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }

    }
}

