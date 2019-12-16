package com.online.edu.eduservice.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.online.edu.common.R;
import com.online.edu.eduservice.handler.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Api(description="上传头像功能")
@RestController
@RequestMapping("eduservice/oss")
@CrossOrigin
public class FileUploadController {

    //上传头像讲师功能
    @PostMapping("upload")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file,@RequestParam(value="host",required = false) String host){

        String endpoint= ConstantPropertiesUtil.ENDPOINT;
        String accessKeyId=ConstantPropertiesUtil.KEYID;
        String accessKeySecret=ConstantPropertiesUtil.KEYSECRET;
        String BucketName=ConstantPropertiesUtil.BUCKETNAME;
        String hostName=ConstantPropertiesUtil.HOST;
        try {
           //1、获取上传文件名称 MultipartFile file
            //在文件名称之前加uuid,防止上传文件名称冲突
            String filename = file.getOriginalFilename();
            String uuid= UUID.randomUUID().toString();
            filename=uuid+filename;   //uuid要加在filename之前，否则加后面会影响后缀名
            //获取当前日期 2019/12/7
            String filePath=new DateTime().toString("yyyy/MM/dd");
            if(!StringUtils.isEmpty(host)){
                hostName=host;
            }
            filename=filePath+"/"+hostName+"/"+filename;
            //2、获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //3、把上传的文件存储到阿里云oss里去
            //创建OSSClient实例
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            //4、 上传字符串。
            ossClient.putObject(BucketName,filename,inputStream);
            // 5、关闭OSSClient。
            ossClient.shutdown();
            String path="http://"+BucketName+"."+endpoint+"/"+filename;
            return R.ok().data("imgurl",path);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
