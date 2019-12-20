package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.query.EduAllCourseDto;
import com.online.edu.eduservice.entity.query.QueryCourse;
import com.online.edu.eduservice.entity.query.TeacherAllInfoDto;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-09
 */
public interface EduCourseService extends IService<EduCourse> {

    //前台根据课程id查询课程详情
    TeacherAllInfoDto getTeacherAllInfo(String id);

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseById(String id);

    boolean updateCourseById(CourseInfoForm courseInfoForm);

    void pageQuery(Page<EduCourse> pageParam, QueryCourse queryCourse);

    boolean deleteCourseById(String id);

    EduAllCourseDto getAllCourseInfo(String id);

    Map<String, Object> getCourseListPage(Page<EduCourse> eduCoursePage);
}
