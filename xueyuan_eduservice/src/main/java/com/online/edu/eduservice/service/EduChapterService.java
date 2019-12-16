package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.query.EduChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-11
 */
//@Repository
public interface EduChapterService extends IService<EduChapter> {

    void deleteChapterByCourseId(String id);

    List<EduChapterDto> getChapterByCourseId(String id);

    boolean deleteById(String id);
}
