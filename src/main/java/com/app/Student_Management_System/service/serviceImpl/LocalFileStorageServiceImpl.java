package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.config.FileStorageProperties;
import com.app.Student_Management_System.exception.FileStorageException;
import com.app.Student_Management_System.service.FileStorageService;
import com.app.Student_Management_System.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFileStorageServiceImpl implements FileStorageService {

    private final FileStorageProperties fileStorageProperties;

    private final FileValidator fileValidator;

    @Override
    public String store(MultipartFile file, String directory) {

        fileValidator.validate(file);
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(
                originalFilename.lastIndexOf('.')+1);
        String filename = UUID.randomUUID()+"."+extension;
        Path uploadPath = Paths.get(fileStorageProperties.getUploadDir(), directory);
        Path filePath = uploadPath.resolve(filename);
        try{
            Files.createDirectories(uploadPath);
            Files.copy(
                    file.getInputStream(), filePath
            );

        }catch (IOException exception){
            throw new FileStorageException("Failed to store file.", exception);
        }
        return String.format("%s/%s" ,directory, filename);
    }
}
