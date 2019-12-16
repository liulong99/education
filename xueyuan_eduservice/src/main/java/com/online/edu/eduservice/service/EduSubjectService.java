package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.query.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importSubject(MultipartFile file);

    List<SubjectNestedVo> nestedList();

    boolean deleteSubjectById(String id);

    boolean addOneLevel(EduSubject eduSubject);

    boolean addTwoLevel(EduSubject eduSubject);
}
