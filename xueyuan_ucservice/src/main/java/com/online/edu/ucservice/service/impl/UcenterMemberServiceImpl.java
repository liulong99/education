package com.online.edu.ucservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.ucservice.entity.UcenterMember;
import com.online.edu.ucservice.mapper.UcenterMemberMapper;
import com.online.edu.ucservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-17
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    //今日注册数
    @Override
    public Integer registerNum(String day) {
        return baseMapper.selectRegisterCount(day);
    }

    //根据登录人的openid判断数据库中是否存在数据
    @Override
    public UcenterMember existWxUser(String openid) {
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }
}
