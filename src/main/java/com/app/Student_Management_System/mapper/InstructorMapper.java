package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InstructorMapper {

    public Instructor toEntity(InstructorRequest request, Department department){

        Instructor instructor = new Instructor();
        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());
        instructor.setDesignation(request.getDesignation());
        instructor.setPhoneNumber(request.getPhoneNumber());
        instructor.setDepartment(department);
        return instructor;
    }

    public InstructorResponse toResponse(Instructor instructor){

        InstructorResponse response = new InstructorResponse();

        response.setId(instructor.getId());
        response.setName(instructor.getName());
        response.setEmail(instructor.getEmail());
        response.setPhoneNumber(instructor.getPhoneNumber());
        response.setDesignation(instructor.getDesignation());
        response.setDepartmentId(instructor.getDepartment().getId());
        return response;
    }

    public List<InstructorResponse> toResponseList(List<Instructor> instructors){
        return instructors.stream()
                .map(this::toResponse)
                .toList();
    }
}
