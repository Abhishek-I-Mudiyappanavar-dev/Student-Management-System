package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.mapper.DepartmentMapper;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.service.DepartmentService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        logger.info("Processing create department request with code '{}'", request.getCode());
        if(departmentRepository.findByCode(request.getCode()).isPresent()){
            logger.warn("Code already associated with other department '{}'", request.getCode());
            throw new DuplicateRequestException("Department already exists with code: "+request.getCode());
        }
        Department department = departmentMapper.toEntity(request);
        department = departmentRepository.save(department);
        DepartmentResponse response = departmentMapper.toResponse(department);
        logger.info("Department created successfully with id '{}'", response.getId());
        return response;
    }

    @Override
    public DepartmentResponse updateDepartment(String id, DepartmentRequest request) {
        logger.info("Processing update request for department with id '{}'", id);
        Department department = departmentRepository.findById(id).orElseThrow(()-> {
            logger.warn("No department found with id '{}'", id);
            return new NoSuchElementException("There is no department with id: "+id);
        });
        if(!department.getName().equals(request.getName())){

        }
        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setDescription(request.getDescription());

        department = departmentRepository.save(department);
        DepartmentResponse response = departmentMapper.toResponse(department);
        logger.info("Department updated successfully with id '{}'", id);
        return response;
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toResponseList(departments);
    }

    @Override
    public DepartmentResponse getDepartmentById(String id) {
        return departmentMapper.toResponse(departmentRepository.findById(id).orElseThrow(()-> {
            logger.warn("No department found with id '{}'", id);
            return new NoSuchElementException("There is no department with id: "+id);
        }));
    }

    @Override
    public DepartmentResponse getDepartmentByName(String name) {
        Department department = departmentRepository.findByName(name).orElseThrow(()-> {
            logger.warn("No department found with name '{}'", name);
            return new NoSuchElementException("There is no department with name: "+name);
        });
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse getDepartmentByCode(String code) {
        return departmentMapper.toResponse(departmentRepository.findByCode(code).orElseThrow(()-> {
            logger.warn("No department found with code '{}'", code);
            return new NoSuchElementException("There is no department with code: "+code);
        }));
    }

    @Override
    public void deleteDepartmentById(String id) {
        logger.info("Processing delete department with id '{}'", id);
        Department department = departmentRepository.findById(id).orElseThrow(()-> {
            logger.warn("No department found with id '{}'", id);
            return new NoSuchElementException("There is no department with id: "+id);
        });
        departmentRepository.delete(department);
        logger.info("Department deleted successfully with id '{}'", id);
    }

    @Override
    public Boolean existsDepartmentByCode(String code) {

        return null;
    }

    @Override
    public Boolean existsDepartmentByName(String name) {
        return null;
    }

}
