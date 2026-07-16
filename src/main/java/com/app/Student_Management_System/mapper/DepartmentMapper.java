package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;
import com.app.Student_Management_System.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper{

    public Department toEntity(DepartmentRequest request) {

        Department department = new Department();
        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setDescription(request.getDescription());
        return department;
    }

    public DepartmentResponse toResponse(Department department) {

        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setCode(department.getCode());
        response.setName(department.getName());
        response.setDescription(department.getDescription());
        return response;
    }

    public List<DepartmentResponse> toResponseList(List<Department> departments) {

        return departments.stream()
                .map(this::toResponse)
                .toList();
    }
}
