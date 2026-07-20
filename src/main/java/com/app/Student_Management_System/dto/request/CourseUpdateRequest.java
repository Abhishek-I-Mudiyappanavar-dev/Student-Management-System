package com.app.Student_Management_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Request payload for updating a course")
@Getter
@Setter
public class CourseUpdateRequest {

    private String title;

    private String description;

    private String instructorId;
}
