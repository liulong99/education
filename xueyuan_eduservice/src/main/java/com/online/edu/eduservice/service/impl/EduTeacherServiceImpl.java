package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduTeacherMapper;
import com.online.edu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //条件查询带分页
    @Override
    public void pageListCondition(Page<EduTeacher> pageList, QueryTeacher queryTeacher) {

//        try {
//            int i=9/0;
//        } catch (Exception e) {
//            //抛出自定义异常
//            throw new EduException(20001,"执行了自定义异常");
//        }
        //关键：queryTeacher有传来的条件值，判断，如果有条件值则拼接
        if(queryTeacher==null){
            //直接进行分页查询，不进行条件操作
            baseMapper.selectPage(pageList,null);
            return ;
        }
        //如果queryTeacher不为空
        //从queryTeacher中获取条件值
        String name = queryTeacher.getName();
        String  level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        QueryWrapper wrapper=new QueryWrapper();
        if(!StringUtils.isEmpty
                (name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageList,wrapper);
    }

    //根据id删除
    @Override
    public boolean deleteTeacherById(String id) {
        int i = baseMapper.deleteById(id);
        return i>0;
    }

}
