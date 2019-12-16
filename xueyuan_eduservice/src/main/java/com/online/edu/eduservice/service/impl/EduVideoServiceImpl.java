package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //根据课程id删除小节的方法
    @Override
    public void deleteVideoByCourseId(String id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }

    //根据id删除小节的方法
    @Override
    public boolean deleteById(String id) {
        //删除视频资源 TODO
        int i = baseMapper.deleteById(id);
        return i>0;
    }
}
