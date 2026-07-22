package com.app.Student_Management_System.dto.response;

import com.app.Student_Management_System.enums.EnrollmentStatus;
import com.app.Student_Management_System.enums.Semester;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class EnrollmentResponse {

    private String id;

    private String studentId;
    private String studentName;

    private String courseId;
    private String courseCode;
    private String courseTitle;

    private Semester semester;

    private String academicYear;

    private EnrollmentStatus status;

    private LocalDate enrollmentDate;
}
