package com.online.vod.vidservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoAdminService {

    String uploadVideo(MultipartFile file);

    void deleteVideo(String id);

    void deleteMoreVideo(List videoList);

}
