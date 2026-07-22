package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Semester;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EnrollmentRequest {

    @NotBlank
    private String studentId;

    @NotBlank
    private String courseId;

    @NotNull
    private Semester semester;

    private String academicYear;
}
