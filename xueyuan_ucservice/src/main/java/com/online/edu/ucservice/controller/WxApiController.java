package com.online.edu.ucservice.controller;

import com.online.edu.ucservice.service.UcenterMemberService;
import com.online.edu.ucservice.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Api(value="微信生成二维码")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //扫描二维码进行回调的方法
    @ApiOperation(value = "扫描二维码进行回调的方法")
    @GetMapping("callback")
    public String callback(){
        return null;
    }

    @ApiOperation(value="生成二维码的方法")
    @GetMapping("login")
    public String genQrConnect(HttpSession session){
        //1、微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //2、回调地址
        String redirectUrl= ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            //3、url编码
            redirectUrl= URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //4、TODO 为了测试：这个值传递是注册内网穿透的域名名称，实现域名跳转
        //为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        String state="atonline";
        System.out.println("state = " + state);
        //5、生成qrcodeUrl
        String qrcodeUrl=String.format(baseUrl,ConstantPropertiesUtil.WX_OPEN_APP_ID,redirectUrl,state);
        return "redirect:"+qrcodeUrl;
    }
}
