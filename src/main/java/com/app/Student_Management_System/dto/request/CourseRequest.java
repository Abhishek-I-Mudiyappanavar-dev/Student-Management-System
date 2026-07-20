package com.app.Student_Management_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Schema(description = "Request payload for creating or updating a course")
public class CourseRequest {

    @Schema(
            description = "Unique title of the course",
            example = "Data Structures"
    )
    @NotBlank
    private String title;

    @Schema(
            description = "Unique course code",
            example = "CS201"
    )
    @NotBlank
    private String code;

    @Schema(
            description = "Number of academic credits assigned to the course",
            example = "4"
    )
    @NotNull
    private Integer credits;

    @Schema(
            description = "Brief description of the course content",
            example = "Covers arrays, linked lists, stacks, queues, trees, graphs, and algorithm analysis."
    )
    private String description;

    @Schema(
            description = "Unique identifier of the department offering the course",
            example = "d7b8c1f3-4a6d-4c2e-9d5b-8e3f7a2c1b6d"
    )
    @NotBlank
    private String departmentId;

    @Schema(
            description = "Unique identifier of the instructor assigned to the course. This field is optional during course creation.",
            example = "8f2a4b1c-7d9e-4f3a-a2c8-5b1d9e7f6a4c"
    )
    private String instructorId;
}
