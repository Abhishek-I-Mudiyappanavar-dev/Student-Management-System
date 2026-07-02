package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.mapper.DepartmentMapper;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.service.DepartmentService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if(departmentRepository.findByCode(request.getCode()).isPresent()){
            throw new DuplicateRequestException("Department already exists with code: "+request.getCode());
        }
        Department department = departmentMapper.toEntity(request);
        department = departmentRepository.save(department);
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(String id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("There is no department with id: "+id));
        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setDescription(request.getDescription());

        department = departmentRepository.save(department);
        return departmentMapper.toResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toResponseList(departments);
    }

    @Override
    public DepartmentResponse getDepartmentById(String id) {
        return departmentMapper.toResponse(departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("There is no department with id: "+id)));
    }

    @Override
    public DepartmentResponse getDepartmentByName(String name) {
        Department department = departmentRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("There is no department with name: "+name));
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse getDepartmentByCode(String code) {
        return departmentMapper.toResponse(departmentRepository.findByCode(code).orElseThrow(()-> new NoSuchElementException("There is no department with code: "+code)));
    }

    @Override
    public void deleteDepartmentById(String id) {

        Department department = departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("There is no department with id: "+id));
        departmentRepository.delete(department);
    }

}
