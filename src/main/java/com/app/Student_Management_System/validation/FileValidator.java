package com.app.Student_Management_System.validation;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {

    void validate(MultipartFile file);
}
