package com.online.edu.ucservice.controller;


import com.online.edu.common.R;
import com.online.edu.ucservice.entity.UcenterMember;
import com.online.edu.ucservice.service.UcenterMemberService;
import com.online.edu.ucservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-17
 */
@RestController
@CrossOrigin
@RequestMapping("/ucservice/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation(value="今日注册数")
    @GetMapping("registerNum/{day}")
    public R registerNum(@PathVariable String day){
        Integer count=ucenterMemberService.registerNum(day);
        return  R.ok().data("countRegister", count);
    }

    //根据token信息jwt的令牌，获取用户信息返回
    @ApiOperation(value="根据token信息jwt的令牌，获取用户信息返回")
    @GetMapping("userInfo/{token}")
    public R getUserInfoToken(@PathVariable String token) {
        //调用工具类获取用户信息
        Claims claims = JwtUtils.checkJwt(token);
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String id = (String)claims.get("id");

        UcenterMember member = new UcenterMember();
        member.setId(id);
        member.setId(avatar);
        member.setNickname(nickname);

        return R.ok().data("member",member);
    }

}

