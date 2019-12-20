package com.online.vod.vidservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.online.vod.vidservice.service.VideoAdminService;
import com.online.vod.vidservice.utils.AliyunVodSDKUtils;
import com.online.vod.vidservice.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VideoAdminServiceImpl implements VideoAdminService {
    //阿里云上传视频,返回视频id
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.KEY_ID,
                    ConstantPropertiesUtil.KEY_SECRET,
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            return videoId;
        } catch (Exception e) {
           e.printStackTrace();
            return null;
        }
    }

    //删除阿里云视频
    @Override
    public void deleteVideo(String id) {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.KEY_ID,
                    ConstantPropertiesUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            DeleteVideoResponse response = client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //删除多个视频的方法
    @Override
    public void deleteMoreVideo(List videoList) {
        try {
            //初始化操作
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);
            //创建删除视频请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置删除多个视频id
            //videoList集合中多个视频id传递到删除的方法里面
            //把list集合变成 1,2,3
            //join方法里面第一个参数是数组
            //第二个参数 根据什么内容进行分割 ,
            String videoIds = StringUtils.join(videoList.toArray(), ",");
            //多个视频id使用,隔开 1,2,3
            request.setVideoIds(videoIds);
            //调用方法实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //根据视频id获取播放凭证
    @Override
    public String getPalyAuth(String vid) {
        String playAuth=null;
        try {
            //初始化客户端、请求对象和相应对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.KEY_ID,
                    ConstantPropertiesUtil.KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //设置请求参数
            request.setVideoId(vid);
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
            playAuth = response.getPlayAuth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playAuth;
    }

}
