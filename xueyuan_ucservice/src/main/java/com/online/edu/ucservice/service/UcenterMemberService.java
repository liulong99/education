package com.online.edu.ucservice.service;

import com.online.edu.ucservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-17
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer registerNum(String day);
}
