package com.app.Student_Management_System.validation.implementation;

import com.app.Student_Management_System.config.FileStorageProperties;
import com.app.Student_Management_System.exception.InvalidFileException;
import com.app.Student_Management_System.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class FileValidatorImpl implements FileValidator {

    private final FileStorageProperties fileStorageProperties;

    private static final Set<String > ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final Set<String > ALLOWED_EXTENSIONS = Set.of(
            "jpg",
            "jpeg",
            "png",
            "webp"
    );

    @Override
    public void validate(MultipartFile file) {

        if(file==null || file.isEmpty()){
            throw new IllegalArgumentException("File cannot be empty.");
        }

        if(file.getSize() > fileStorageProperties.getMaxSize().toBytes()){
            throw new IllegalArgumentException("File size exceeds the maximum allowed limit.");
        }

        String contentType = file.getContentType();

        if(contentType==null || !ALLOWED_CONTENT_TYPES.contains(contentType)){
            throw new InvalidFileException("Only JPEG, PNG and WEBP images are allowed.");
        }

        String filename = file.getOriginalFilename();

        if(filename==null || !filename.contains(".")){
            throw new InvalidFileException("Invalid file name.");
        }

        String extension = filename.substring(filename.lastIndexOf('.')+1).toLowerCase();

        if(!ALLOWED_EXTENSIONS.contains(extension)){
            throw new InvalidFileException("Unsupported file extension.");
        }
    }
}
