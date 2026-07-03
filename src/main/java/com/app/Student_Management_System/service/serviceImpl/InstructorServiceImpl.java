package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Instructor;
import com.app.Student_Management_System.enums.Designation;
import com.app.Student_Management_System.mapper.InstructorMapper;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.repository.InstructorRepository;
import com.app.Student_Management_System.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    private final InstructorMapper instructorMapper;

    private final DepartmentRepository departmentRepository;

    @Override
    public List<InstructorResponse> getAllInstructors() {
        return instructorMapper.toResponseList(instructorRepository.findAll());
    }

    @Override
    public List<InstructorResponse> getInstructorsByDepartmentId(String id) {
        if(departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("There is no department with id: "+id);
        }
        return instructorMapper.toResponseList(instructorRepository.findByDepartmentId(id));
    }

    @Override
    public InstructorResponse getInstructorById(String id) {
        return instructorMapper.toResponse(instructorRepository.findById(id).orElseThrow(()-> new NoSuchElementException("There is no instructor with id: "+id)));
    }

    @Override
    public List<InstructorResponse> getInstructorsByName(String name) {
        return instructorMapper.toResponseList(instructorRepository.findByName(name));
    }

    @Override
    public InstructorResponse getInstructorByEmail(String email) {
        return instructorMapper.toResponse(instructorRepository.findByEmail(email).orElseThrow(()-> new NoSuchElementException("There is no instructor with email id: "+email)));
    }

    @Override
    public List<InstructorResponse> getInstructorByDesignation(Designation designation) {
        return instructorMapper.toResponseList(instructorRepository.findByDesignation(designation));
    }

    @Override
    public InstructorResponse createInstructor(InstructorRequest request) {
        if(instructorRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UnsupportedOperationException("Email already associated with existing instructor: "+request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> new NoSuchElementException("No department found with id: "+request.getDepartmentId()));
        return instructorMapper.toResponse(instructorRepository.save(instructorMapper.toEntity(request, department)));//
    }

    @Override
    public InstructorResponse updateInstructor(String id, InstructorRequest request) {
        Instructor existingInstructor = instructorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No instructor found with id: " + id));

        instructorRepository.findByEmail(request.getEmail())
                .ifPresent(student -> {
                    if (!existingInstructor.getId().equals(student.getId())) {
                        throw new UnsupportedOperationException(
                                "Email already associated with other student: " + request.getEmail()
                        );
                    }
                });

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> new NoSuchElementException("No department found with id: "+request.getDepartmentId()));

        existingInstructor.setName(request.getName());
        existingInstructor.setEmail(request.getEmail());
        existingInstructor.setPhoneNumber(request.getPhoneNumber());
        existingInstructor.setDesignation(request.getDesignation());
        existingInstructor.setDepartment(department);

        return instructorMapper.toResponse(instructorRepository.save(existingInstructor));
    }

    @Override
    public void deleteInstructorById(String id) {

        Instructor instructor = instructorRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Instructor not found with id: "+id));
        instructorRepository.delete(instructor);
    }
}
