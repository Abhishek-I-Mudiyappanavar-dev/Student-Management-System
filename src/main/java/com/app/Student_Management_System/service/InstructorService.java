package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.enums.Designation;

import java.util.List;

public interface InstructorService {

    List<InstructorResponse> getAllInstructors();

    List<InstructorResponse> getInstructorsByDepartmentId(String id);

    InstructorResponse getInstructorById(String id);

    List<InstructorResponse> getInstructorsByName(String name);

    InstructorResponse getInstructorByEmail(String email);

    List<InstructorResponse> getInstructorByDesignation(Designation designation);

    InstructorResponse createInstructor(InstructorRequest request);

    InstructorResponse updateInstructor(String id, InstructorRequest request);

    void deleteInstructorById(String id);
}
