package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for creating a new student")
public class StudentRequest {

    @Schema(
            description = "Student's first name",
            example = "Abhishek"
    )
    @NotBlank
    private String firstName;

    @Schema(
            description = "Student's last name",
            example = "Mudiyappanavar"
    )
    private String lastName;

    @Schema(
            description = "Unique email address of the student",
            example = "abhishek@example.com",
            format = "email"
    )
    @NotBlank
    @Email
    private String email;

    @Schema(
            description = "Student's contact phone number",
            example = "9876543210"
    )
    @NotBlank
    private String phoneNumber;

    @Schema(
            description = "Gender of the student",
            example = "MALE",
            allowableValues = {
                    "MALE",
                    "FEMALE",
                    "OTHER"
            }
    )
    @NotNull
    private Gender gender;

    @Schema(
            description = "Unique identifier of the department in which the student is enrolled",
            example = "b4e8f6a9-2d9d-4d9d-9c30-12f4d3c4d8ef"
    )
    @NotBlank
    private String departmentId;

    @Schema(
            description = "Student's date of birth",
            example = "2003-07-15",
            type = "string",
            format = "date"
    )
    @NotNull
    private LocalDate dateOfBirth;
}