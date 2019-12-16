package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-11
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoByCourseId(String id);

    boolean deleteById(String id);

}
