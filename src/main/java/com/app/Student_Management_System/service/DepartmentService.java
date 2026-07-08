package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse updateDepartment(String id, DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(String id);

    DepartmentResponse getDepartmentByName(String name);

    DepartmentResponse getDepartmentByCode(String code);

    void deleteDepartmentById(String id);

    Boolean existsDepartmentByCode(String code);

    Boolean existsDepartmentByName(String name);

}
