package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.EnrollmentRequest;
import com.app.Student_Management_System.dto.request.EnrollmentUpdateRequest;
import com.app.Student_Management_System.dto.response.EnrollmentResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.enums.EnrollmentStatus;
import com.app.Student_Management_System.enums.Semester;
import org.springframework.data.domain.Pageable;

public interface EnrollmentService {

    PageResponse<EnrollmentResponse>
    search(String studentId, String courseCode, Semester semester,
           String courseTitle, EnrollmentStatus status, String academicYear, String departmentCode, Pageable pageable);

    EnrollmentResponse enrollCourse(EnrollmentRequest request);

    EnrollmentResponse
    updateEnrollment(String enrollmentId , EnrollmentUpdateRequest request);

    EnrollmentResponse getEnrollmentById(String enrollmentId);

    void deleteEnrollment(String enrollmentId);

}
