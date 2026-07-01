package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse admitStudent(StudentRequest request);

    StudentResponse updateStudentInfo(String id, StudentRequest request);

    void deleteStudentById(String id);

    List<StudentResponse> getStudentsByDepartmentId(String departmentId);

    StudentResponse getStudentById(String id);

    StudentResponse getStudentByEmail(String email);

    List<StudentResponse> getStudentsByFirstName(String firstName);

    List<StudentResponse> getStudentsByLastName(String lastName);

    List<StudentResponse> getAllStudents();

}
