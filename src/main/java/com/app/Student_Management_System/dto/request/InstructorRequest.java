package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Designation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Request payload for creating a new instructor")
public class InstructorRequest {

    @Schema(
            description = "Full name of the instructor",
            example = "Dr. Jane Smith"
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Academic designation of the instructor",
            example = "ASSISTANT_PROFESSOR",
            allowableValues = {
                    "LECTURER",
                    "ASSISTANT_PROFESSOR",
                    "ASSOCIATE_PROFESSOR",
                    "PROFESSOR",
                    "HEAD_OF_DEPARTMENT"
            }
    )
    @NotNull
    private Designation designation;

    @Schema(
            description = "Unique email address of the instructor",
            example = "jane.smith@university.edu",
            format = "email"
    )
    @NotBlank
    @Email
    private String email;

    @Schema(
            description = "Instructor's contact phone number",
            example = "9876543210"
    )
    @NotBlank
    private String phoneNumber;

    @Schema(
            description = "Unique identifier of the department to which the instructor belongs",
            example = "b4e8f6a9-2d9d-4d9d-9c30-12f4d3c4d8ef"
    )
    @NotBlank
    private String departmentId;
}