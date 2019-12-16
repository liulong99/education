package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.query.SubjectNestedVo;
import com.online.edu.eduservice.entity.query.SubjectVo;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduSubjectMapper;
import com.online.edu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.poi.hssf.record.DVALRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importSubject(MultipartFile file) {
        //返回错误信息的list集合
        List<String>msg=new ArrayList<>();
        try{
            InputStream inputStream = file.getInputStream();
            Workbook workbook=new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //循环遍历获取行
            int lastRowNum = sheet.getLastRowNum();
            for(int i=1;i<=lastRowNum;i++){
                Row row = sheet.getRow(i);
                if(row==null){
                    continue;
                }
                Cell cellOne = row.getCell(0);
                if(cellOne==null){
                    String str="第"+i+"行数据为空，第"+1+"列为空";
                    msg.add(str);
                    //跳过这一行，往下继续执行
                    continue;
                }
                //获取第一列的值
                String cellOneValue = cellOne.getStringCellValue();
                //用来存储一级分类的id,即 二级分类的parent_id
                String id_parent=null;
                //奖以及分类的值插入到数据库
                EduSubject eduSubject1 = this.existOneSubject(cellOneValue);
                if(eduSubject1==null){
                    //如果数据库中不存在相同的一级分类
                    EduSubject eduSubject=new EduSubject();
                    eduSubject.setParentId("0");
                    eduSubject.setTitle(cellOneValue);
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                    id_parent=eduSubject.getId();
                }else{
                    //存在
                    id_parent=eduSubject1.getId();
                }

                Cell cellTwo = row.getCell(1);
                if(cellTwo==null){
                    String str="第"+i+"行数据为空，第"+2+"列为空";
                    msg.add(str);
                    continue;
                }
                //获取第二列的值
                String cellTwoValue = cellTwo.getStringCellValue();
                //将第二列中的值存到数据库中
                EduSubject eduSubject2 = this.existTwoSubject(cellTwoValue, id_parent);
                if(eduSubject2==null){
                    //数据库中不存在二级分类。可以添加
                    EduSubject eduSubject=new EduSubject();
                    eduSubject.setTitle(cellTwoValue);
                    eduSubject.setParentId(id_parent);
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                }
            }
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(20001,"导入数据失败出现了异常");
        }
    }

    @Override
    public List<SubjectNestedVo> nestedList() {
        //最终要得到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList=new ArrayList<>();
        //获取一级分类数据记录
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("parent_id",0); //eq表示==
        wrapper.orderByAsc("sort","id");
        List<EduSubject> subjects=baseMapper.selectList(wrapper);

        //获取二级分类数据记录
        QueryWrapper<EduSubject>queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("parent_id",0);  //ne表示！=
        queryWrapper.orderByAsc("sort","id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);

        //填充一级分类vo数据
        int count =subjects.size();
        for (int i = 0; i <count;i++){
            EduSubject subject=subjects.get(i);
            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo=new SubjectNestedVo();
            BeanUtils.copyProperties(subject,subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);

            //填充二级分类vo数据
            ArrayList<SubjectVo>subjectVoArrayList=new ArrayList<>();
            int count2=eduSubjects.size();
            for (int j = 0; j < count2; j++) {
                EduSubject eduSubject=eduSubjects.get(j);
                if(subject.getId().equals(eduSubject.getParentId())){
                    //创建二级类别vo对象
                    SubjectVo subjectVo=new SubjectVo();
                    BeanUtils.copyProperties(eduSubject,subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoArrayList);

        }
        return subjectNestedVoArrayList;
    }

    //删除分类
    @Override
    public boolean deleteSubjectById(String id) {
        //判断一级分类下面有二级分类
        //根据parent_id查询
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //判断如果有二级分类
        if (count>0) {
            return false;
        } else {//没有二级分类
            //进行删除
            int result = baseMapper.deleteById(id);
            return result>0;
        }
    }

    //添加一级分类
    @Override
    public boolean addOneLevel(EduSubject eduSubject) {
        //判断一级分类是否已经存在
        EduSubject eduSubject1 = existOneSubject(eduSubject.getTitle());
        if(eduSubject1==null){
            eduSubject.setParentId("0");
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        }
        return false;
    }

    //添加二级分类
    @Override
    public boolean addTwoLevel(EduSubject eduSubject) {
        EduSubject eduSubject2 = existTwoSubject(eduSubject.getTitle(), eduSubject.getParentId());
        if(eduSubject2==null){
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        }
        return false;
    }

    //判断是否存在二级分类
    private EduSubject existTwoSubject(String name,String id){
        QueryWrapper<EduSubject>wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        wrapper.eq("title",name);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    //判断是否存在一级分类
    private EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
}
