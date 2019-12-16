package com.online.edu.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.query.EduAllCourseDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liulong
 * @since 2019-12-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {


    EduAllCourseDto getAllCourseInfo(String id);

}
