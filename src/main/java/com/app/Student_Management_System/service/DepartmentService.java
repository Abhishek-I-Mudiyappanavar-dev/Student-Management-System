package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse updateDepartment(String id, DepartmentRequest request);

    PageResponse<DepartmentResponse> getAllDepartments(Pageable pageable);

    DepartmentResponse getDepartmentById(String id);

    PageResponse<DepartmentResponse> getDepartmentByName(String name, Pageable pageable);

    DepartmentResponse getDepartmentByCode(String code);

    void deleteDepartmentById(String id);

}
