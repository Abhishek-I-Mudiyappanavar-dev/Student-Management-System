package com.app.Student_Management_System.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for creating or updating a department")
public class DepartmentRequest {

    @Schema(
            description = "Unique name of the department",
            example = "Computer Science and Engineering"
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Unique department code",
            example = "CSE"
    )
    @NotBlank
    private String code;

    @Schema(
            description = "Brief description of the department and its academic focus",
            example = "Offers undergraduate and postgraduate programs in computer science, software engineering, artificial intelligence, and related disciplines."
    )
    @NotBlank
    private String description;
}