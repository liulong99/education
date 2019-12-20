package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.common.util.PriceConstants;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.EduAllCourseDto;
import com.online.edu.eduservice.entity.query.QueryCourse;
import com.online.edu.eduservice.entity.query.TeacherAllInfoDto;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    //前台根据课程id查询课程详情
    @Override
    public TeacherAllInfoDto getTeacherAllInfo(String id) {
        return baseMapper.getTeacherAllInfo(id);
    }

    //新增课程
    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        EduCourse eduCourse=new EduCourse();
        //复制
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        //保存基本信息
        boolean save = this.save(eduCourse);
        if(!save){
            return null;
        }else{
            //保存课程详细信息
            EduCourseDescription eduCourseDescription=new EduCourseDescription();
            BeanUtils.copyProperties(courseInfoForm,eduCourseDescription);
            eduCourseDescription.setId(eduCourse.getId());
            boolean save1 = eduCourseDescriptionService.save(eduCourseDescription);
            if(!save1){
                return null;
            }
            return eduCourse.getId();
        }
    }

    //根据id查询课程基本信息
    @Override
    public CourseInfoForm getCourseById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse==null){
            throw new EduException(20001,"没有课程基本信息");
        }
        CourseInfoForm courseInfoForm=new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        //到这一步只有课程的详细信息没有封装
        EduCourseDescription eduCourseDescriptionServiceById = eduCourseDescriptionService.getById(id);
        String description = eduCourseDescriptionServiceById.getDescription();
        courseInfoForm.setDescription(description);
        courseInfoForm.setPrice(courseInfoForm.getPrice().setScale(PriceConstants.DISPLAY_SCALE, BigDecimal.ROUND_FLOOR));
        return courseInfoForm;
    }

    //根据id修改课程信息
    @Override
    public boolean updateCourseById(CourseInfoForm courseInfoForm) {
        //修改课程基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i==0){
            throw new EduException(20001,"修改分类失败");
        }
        //修改描述表
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        return b;
    }

    //分页课程列表查询
    @Override
    public void pageQuery(Page<EduCourse> pageParam, QueryCourse queryCourse) {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create","gmt_modified");
        if(queryCourse==null){
            baseMapper.selectPage(pageParam,wrapper);
            return;
        }
        String title=queryCourse.getTitle();
        String teacherId=queryCourse.getTeacherId();
        String subjectParentId=queryCourse.getTeacherId();
        String subjectId=queryCourse.getSubjectId();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id", teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.ge("subject_parent_id", subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam,wrapper);
    }

    @Override
    public boolean deleteCourseById(String id) {

        //删除章节
        eduChapterService.deleteChapterByCourseId(id);
        //删除小节
        eduVideoService.deleteVideoByCourseId(id);
        //删除课程描述
        eduCourseDescriptionService.deleteCourseDescriptionByCourseId(id);
        //删除课程
        int i = baseMapper.deleteById(id);
        return i>0;
    }

    //根据课程id查询课程所有信息
    @Override
    public EduAllCourseDto getAllCourseInfo(String id) {
        EduAllCourseDto allCourseInfo = baseMapper.getAllCourseInfo(id);
        return allCourseInfo;
    }

    //前端分页查询课程列表
    @Override
    public Map<String, Object> getCourseListPage(Page<EduCourse> eduCoursePage) {
        baseMapper.selectPage(eduCoursePage,null);
        List<EduCourse> records=eduCoursePage.getRecords();//每页的数据
        long current = eduCoursePage.getCurrent();//当前页
        long pages = eduCoursePage.getPages();//总页数
        long total = eduCoursePage.getTotal();//总记录数
        long size = eduCoursePage.getSize();//每页显示的记录数
        boolean hasPrevious = eduCoursePage.hasPrevious();//是否有前一页
        boolean hasNext = eduCoursePage.hasNext();//是否有后一页

        //把分页的数据放到map中
        Map<String,Object>map=new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        return map;
    }


}
