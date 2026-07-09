package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.request.InstructorUpdateRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.enums.Designation;
import com.app.Student_Management_System.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    @GetMapping
    public ResponseEntity<List<InstructorResponse>> getAllInstructors(){
        logger.info("Received GET /instructors request");
        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<InstructorResponse> getInstructorById(@PathVariable String instructorId){
        logger.info("Received GET /instructors/{} request", instructorId);
        return ResponseEntity.ok(instructorService.getInstructorById(instructorId));
    }

    @GetMapping("/name")
    public ResponseEntity<List<InstructorResponse>> getInstructorsByName(@RequestParam String name){
        logger.info("Received GET /instructors/name request for name {}", name);
        return ResponseEntity.ok(instructorService.getInstructorsByName(name));
    }

    @GetMapping("/designation")
    public ResponseEntity<List<InstructorResponse>> getInstructorsByDesignation(@RequestParam Designation designation){
        logger.info("Received GET /instructors/designation request for designation {}", designation);
        return ResponseEntity.ok(instructorService.getInstructorByDesignation(designation));
    }

    @GetMapping("/email")
    public ResponseEntity<InstructorResponse> getInstructorByEmail(@RequestParam String email){
        logger.info("Received GET /instructors/email for email {}", email);
        return ResponseEntity.ok(instructorService.getInstructorByEmail(email));
    }

    @PostMapping
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorRequest request){
        logger.info("Received POST /instructors for email {}",request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.createInstructor(request));
    }

    @PatchMapping("/{instructorId}")
    public ResponseEntity<InstructorResponse> updateInstructor(@Valid @RequestBody InstructorUpdateRequest request, @PathVariable String instructorId){
        logger.info("Received PATCH /instructors/{} request", instructorId);
        return ResponseEntity.ok(instructorService.updateInstructor(instructorId, request));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<InstructorResponse>> getInstructorsByDepartment(@PathVariable String departmentId){
        logger.info("Received GET /instructors/department/{} request", departmentId);
        return ResponseEntity.ok(instructorService.getInstructorsByDepartmentId(departmentId));
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String instructorId){
        logger.info("Received DELETE /instructors/{} request", instructorId);
        instructorService.deleteInstructorById(instructorId);
        return ResponseEntity.noContent().build();
    }
}
