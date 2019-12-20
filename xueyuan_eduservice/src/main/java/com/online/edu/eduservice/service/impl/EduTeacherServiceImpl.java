package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.query.QueryTeacher;
import com.online.edu.eduservice.mapper.EduTeacherMapper;
import com.online.edu.eduservice.service.EduCourseService;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private EduCourseService eduCourseService;

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

    //根据讲师id查询其所讲的全部课程
    @Override
    public List<EduCourse> getCourseByTeacherId(String id) {
        QueryWrapper<EduCourse>wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return list;
    }

    //前台分页查询的方法
    @Override
    public Map<String, Object> getFrontTeacherListPage(Page<EduTeacher> pageTeacher) {
        //调用方法分页查询，用过pageTeacher对象获取分页之后的数据
        baseMapper.selectPage(pageTeacher,null);
        //从pageTeacher分页数据中获取出来数据放到map中
        List<EduTeacher> records = pageTeacher.getRecords();//每页的数据
        long total = pageTeacher.getTotal();//总记录数
        long size = pageTeacher.getSize();//每页显示的记录数
        long current=pageTeacher.getCurrent();//当前页
        long pages = pageTeacher.getPages();//总页数
        boolean hasNext = pageTeacher.hasNext();//是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//是否有前一页

        //把分页数据放到map集合中
        Map<String,Object>map=new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }


}
