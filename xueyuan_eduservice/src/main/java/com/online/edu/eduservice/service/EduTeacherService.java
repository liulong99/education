package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.query.QueryTeacher;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageListCondition(Page<EduTeacher> pageList, QueryTeacher queryTeacher);

    boolean deleteTeacherById(String id);

    List<EduCourse> getCourseByTeacherId(String id);

    Map<String, Object> getFrontTeacherListPage(Page<EduTeacher> pageTeacher);

}
