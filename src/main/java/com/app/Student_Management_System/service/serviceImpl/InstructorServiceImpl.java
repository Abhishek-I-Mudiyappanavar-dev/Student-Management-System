package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.request.InstructorUpdateRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Instructor;
import com.app.Student_Management_System.enums.Designation;
import com.app.Student_Management_System.exception.DepartmentNotFoundException;
import com.app.Student_Management_System.exception.DuplicateEmailException;
import com.app.Student_Management_System.exception.InstructorNotFoundException;
import com.app.Student_Management_System.mapper.InstructorMapper;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.repository.InstructorRepository;
import com.app.Student_Management_System.service.InstructorService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    private final InstructorMapper instructorMapper;

    private final DepartmentRepository departmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(InstructorServiceImpl.class);

    @Override
    public List<InstructorResponse> getAllInstructors() {
        return instructorMapper.toResponseList(instructorRepository.findAll());
    }

    @Override
    public List<InstructorResponse> getInstructorsByDepartmentId(String id) {
        if(departmentRepository.findById(id).isEmpty()){
            logger.warn("No department found with id '{}'", id);
            throw new DepartmentNotFoundException("There is no department with id: "+id);
        }
        return instructorMapper.toResponseList(instructorRepository.findByDepartmentId(id));
    }

    @Override
    public InstructorResponse getInstructorById(String id) {
        return instructorMapper.toResponse(instructorRepository.findById(id).orElseThrow(()->{
            logger.warn("No Instructor found with id '{}'", id);
            return new InstructorNotFoundException("There is no instructor with id: "+id);
        }));
    }

    @Override
    public List<InstructorResponse> getInstructorsByName(String name) {
        return instructorMapper.toResponseList(instructorRepository.findByName(name));
    }

    @Override
    public InstructorResponse getInstructorByEmail(String email) {
        return instructorMapper.toResponse(instructorRepository.findByEmail(email).orElseThrow(()-> {
            logger.warn("No Instructor found with email '{}'", email);
            return new InstructorNotFoundException("There is no instructor with email id: "+email);
        }));
    }

    @Override
    public List<InstructorResponse> getInstructorByDesignation(Designation designation) {
        return instructorMapper.toResponseList(instructorRepository.findByDesignation(designation));
    }

    @Override
    public InstructorResponse createInstructor(InstructorRequest request) {
        logger.info("Processing create Instructor request for email '{}'", request.getEmail());
        if(instructorRepository.findByEmail(request.getEmail()).isPresent()){
            logger.warn("Email already associated with existing instructor '{}'", request.getEmail());
            throw new DuplicateEmailException("Email already associated with existing instructor: "+request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> {
            logger.warn("No department found with id '{}'", request.getDepartmentId());
            return new DepartmentNotFoundException("No department found with id: "+request.getDepartmentId());
        });
        InstructorResponse response = instructorMapper.toResponse(instructorRepository.save(instructorMapper.toEntity(request, department)));//
        logger.info("Instructor created successfully with id '{}' and email '{}'", response.getId(), request.getEmail());
        return response;
    }

    @Override
    public InstructorResponse updateInstructor(String id, InstructorUpdateRequest request){
        logger.info("Processing update request for instructor with id '{}'", id);

        Instructor existingInstructor = instructorRepository.findById(id).orElseThrow(()->{
            logger.warn("No Instructor found with id '{}'", id);
            return new InstructorNotFoundException("No Instructor found with id "+id);
        });

        if(request.getEmail()!=null && !Objects.equals(request.getEmail(), existingInstructor.getEmail())){
            instructorRepository.findByEmail(request.getEmail())
                    .ifPresent(instructor -> {
                        if(!instructor.getId().equals(existingInstructor.getId())){
                            logger.warn("Email already associated with other instructor '{}'",request.getEmail());
                            throw new DuplicateEmailException("Email already associated with other instructor "+request.getEmail());
                        }
                    });
            String previousEmail = existingInstructor.getEmail();
            existingInstructor.setEmail(request.getEmail());
            logger.info("Email updated from '{}' to '{}' for id '{}'", previousEmail, request.getEmail(), id);
        }
        if(request.getName()!=null && !request.getName().isBlank()){
            existingInstructor.setName(request.getName());
        }
        if(request.getPhoneNumber()!=null && !request.getPhoneNumber().isBlank()){
            existingInstructor.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getDesignation()!=null){
            existingInstructor.setDesignation(request.getDesignation());
        }
        InstructorResponse response = instructorMapper.toResponse(instructorRepository.save(existingInstructor));
        logger.info("Instructor updated successfully with id '{}'", id);
        return response;
    }

    @Override
    public void deleteInstructorById(String id) {
        logger.info("Processing delete request by instructor with id '{}'", id);
        Instructor instructor = instructorRepository.findById(id).orElseThrow(()-> {
            logger.warn("No Instructor found with id '{}'", id);
            return new InstructorNotFoundException("Instructor not found with id: "+id);
        });
        instructorRepository.delete(instructor);
        logger.info("Instructor deleted successfully with id '{}'", id);
    }
}
