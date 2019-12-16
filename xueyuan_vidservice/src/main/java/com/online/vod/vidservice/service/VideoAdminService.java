package com.online.vod.vidservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoAdminService {

    String uploadVideo(MultipartFile file);

    void deleteVideo(String id);
}
