package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.request.StudentUpdateRequest;
import com.app.Student_Management_System.dto.response.StudentResponse;
import com.app.Student_Management_System.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping
    public ResponseEntity<StudentResponse> admitStudent(
            @Valid @RequestBody StudentRequest request) {

        logger.info("Received POST /students request for email '{}'.", request.getEmail());
        StudentResponse response = studentService.admitStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> searchStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        logger.info("Received GET /students request [firstName={}, lastName={}].", firstName, lastName);
        return ResponseEntity.ok(
                studentService.searchStudents(firstName, lastName)
        );
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable String studentId) {
        logger.info("Received Get /students for studentId '{}' request.", studentId);
        return ResponseEntity.ok(
                studentService.getStudentById(studentId)
        );
    }

    @GetMapping("/email")
    public ResponseEntity<StudentResponse> getStudentByEmail(
            @RequestParam String email) {
        logger.info("Received GET /students/email for email '{}'",email);
        return ResponseEntity.ok(
                studentService.getStudentByEmail(email)
        );
    }

    @GetMapping("/earnedCredits/{StudentId}")
    public ResponseEntity<Integer> getStudentsEarnedCredits(@PathVariable String studentId){
        logger.info("Received Get /students/earnedCredits for id '{}'",studentId);
        return ResponseEntity.ok(studentService.getEarnedCredits(studentId));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDepartment(
            @PathVariable String departmentId) {
        logger.info("Received GET /students/department request for department Id '{}'",departmentId);
        return ResponseEntity.ok(
                studentService.getStudentsByDepartmentId(departmentId)
        );
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable String studentId,
            @Valid @RequestBody StudentUpdateRequest request) {
        logger.info("Received PATCH /students for studentId '{}'", studentId);
        return ResponseEntity.ok(
                studentService.updateStudentInfo(studentId, request)
        );
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable String studentId) {

        logger.info("Received DELETE /students request for studentId '{}'", studentId);
        studentService.deleteStudentById(studentId);

        return ResponseEntity.noContent().build();
    }
}