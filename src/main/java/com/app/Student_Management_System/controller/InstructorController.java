package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.request.InstructorUpdateRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.enums.Designation;
import com.app.Student_Management_System.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<InstructorResponse>> getAllInstructors(){

        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<InstructorResponse> getInstructorById(@PathVariable String instructorId){

        return ResponseEntity.ok(instructorService.getInstructorById(instructorId));
    }

    @GetMapping("/name")
    public ResponseEntity<List<InstructorResponse>> getInstructorsByName(@RequestParam String name){

        return ResponseEntity.ok(instructorService.getInstructorsByName(name));
    }

    @GetMapping("/designation")
    public ResponseEntity<List<InstructorResponse>> getInstructorsByDesignation(@RequestParam Designation designation){

        return ResponseEntity.ok(instructorService.getInstructorByDesignation(designation));
    }

    @GetMapping("/email")
    public ResponseEntity<InstructorResponse> getInstructorByEmail(@RequestParam String email){

        return ResponseEntity.ok(instructorService.getInstructorByEmail(email));
    }

    @PostMapping
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.createInstructor(request));
    }

    @PatchMapping("/{instructorId}")
    public ResponseEntity<InstructorResponse> updateInstructor(@Valid @RequestBody InstructorUpdateRequest request, @PathVariable String instructorId){

        return ResponseEntity.ok(instructorService.updateInstructor(instructorId, request));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<InstructorResponse>> getInstructorsByDepartment(@PathVariable String departmentId){

        return ResponseEntity.ok(instructorService.getInstructorsByDepartmentId(departmentId));
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String instructorId){

        instructorService.deleteInstructorById(instructorId);
        return ResponseEntity.noContent().build();
    }
}
