package com.app.Student_Management_System.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file, String directory);
}
