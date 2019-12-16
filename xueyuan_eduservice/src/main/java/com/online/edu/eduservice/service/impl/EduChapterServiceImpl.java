package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.entity.query.EduChapterDto;
import com.online.edu.eduservice.entity.query.EduVideoDto;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduChapterMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public void deleteChapterByCourseId(String id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_id",id);
        baseMapper.deleteById(id);
    }

    @Override
    public List<EduChapterDto> getChapterByCourseId(String id) {
        List<EduChapterDto>lists=new ArrayList<>();
        //1、根据课程id查章节
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        //2、根据课程id查小节
        QueryWrapper<EduVideo> wrapper1=new QueryWrapper<>();
        wrapper1.eq("course_id",id);
        List<EduVideo>eduVideos=eduVideoService.list(wrapper1);
        //3、遍历所有的章节，复制值到dto对象里面去
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter chapter=eduChapters.get(i);
            //复制值到dto中去
            EduChapterDto eduChapterDto=new EduChapterDto();
            BeanUtils.copyProperties(chapter,eduChapterDto);
            lists.add(eduChapterDto);

            List<EduVideoDto> videoDtoLists=new ArrayList<>();
            //遍历小节，将小节的值复制到dto对象中
            for (int j = 0; j < eduVideos.size(); j++) {
                //复制,取每一个小节
                EduVideo eduVideo=eduVideos.get(j);
                if(eduVideo.getChapterId().equals(chapter.getId())){
                    EduVideoDto eduVideoDto=new EduVideoDto();
                    BeanUtils.copyProperties(eduVideo,eduVideoDto);
                    videoDtoLists.add(eduVideoDto);
                }
            }
            eduChapterDto.setChildren(videoDtoLists);
        }
        return lists;
    }

    //根据id删除章节的方法
    @Override
    public boolean deleteById(String id) {
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = eduVideoService.count(wrapper);
        if(count!=0){
            //表示章节下面有小节，不能删除，抛出异常
            throw new EduException(20001,"该章节下存在小节，请先删除小节");
        }
        //没有小节，可以删除章节
        int i = baseMapper.deleteById(id);
        return i>0;
    }
}
