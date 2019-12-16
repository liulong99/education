package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-09
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void deleteCourseDescriptionByCourseId(String id);
}
