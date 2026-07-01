package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.response.StudentResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Student;

import java.util.List;

public interface StudentMapper {

     Student toEntity(StudentRequest request, Department department);
     StudentResponse toResponse(Student student);

     List<StudentResponse> toResponseList(List<Student> students);

}
