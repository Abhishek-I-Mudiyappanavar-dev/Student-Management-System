package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.request.InstructorUpdateRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.enums.Designation;
import org.springframework.data.domain.Pageable;

public interface InstructorService {

    PageResponse<InstructorResponse> searchInstructors(String name,String departmentId, Designation designation, Pageable pageable);

    InstructorResponse getInstructorById(String id);

    InstructorResponse getInstructorByEmail(String email);

    InstructorResponse appointInstructor(InstructorRequest request);

    InstructorResponse updateInstructor(String id, InstructorUpdateRequest request);

    void deleteInstructorById(String id);
}
