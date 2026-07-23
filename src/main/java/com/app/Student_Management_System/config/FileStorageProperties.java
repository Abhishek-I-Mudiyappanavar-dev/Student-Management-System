package com.app.Student_Management_System.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@Component
@ConfigurationProperties(prefix = "app.file")
@Setter
@Getter
public class FileStorageProperties {

    private String uploadDir;

    private DataSize maxSize;

}
