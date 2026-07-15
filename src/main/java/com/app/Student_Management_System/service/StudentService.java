package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.request.StudentUpdateRequest;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.dto.response.StudentResponse;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StudentService {

    StudentResponse admitStudent(StudentRequest request);

    StudentResponse updateStudentInfo(String id, StudentUpdateRequest request);

    void deleteStudentById(String id);

    StudentResponse getStudentById(String id);

    StudentResponse getStudentByEmail(String email);

    PageResponse<StudentResponse> searchStudents(String firstName, String lastName,String departmentId, Pageable pageable);

    Integer getEarnedCredits(String id);

}
