package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.response.StudentResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentMapperImpl implements StudentMapper{

    @Override
    public Student toEntity(StudentRequest request, Department department) {

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setGender(request.getGender());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setDepartment(department);
        return student;
    }

    @Override
    public StudentResponse toResponse(Student student) {

        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setGender(student.getGender());
        response.setEmail(student.getEmail());
        response.setPhoneNumber(student.getPhoneNumber());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setDepartmentId(student.getDepartment().getId());
        response.setDepartmentName(student.getDepartment().getName());
        response.setEarnedCredits(student.getEarnedCredits());
        return response;
    }

    @Override
    public List<StudentResponse> toResponseList(List<Student> students) {

        return students.stream()
                .map(this::toResponse)
                .toList();
    }
}
