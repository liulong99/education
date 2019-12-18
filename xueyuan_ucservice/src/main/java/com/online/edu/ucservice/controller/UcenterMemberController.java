package com.online.edu.ucservice.controller;


import com.online.edu.common.R;
import com.online.edu.ucservice.service.UcenterMemberService;
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

}

