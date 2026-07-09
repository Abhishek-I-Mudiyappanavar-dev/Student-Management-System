package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;
import com.app.Student_Management_System.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        logger.info("Received POST /departments request for code {}", request.getCode());
        DepartmentResponse response =
                departmentService.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        logger.info("Received GET /departments request");
        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable String id) {
        logger.info("Received GET /departments/{} request",id);
        return ResponseEntity.ok(
                departmentService.getDepartmentById(id)
        );
    }

    @GetMapping("/name")
    public ResponseEntity<DepartmentResponse> getDepartmentByName(
            @RequestParam String name) {
        logger.info("Received GET /departments/name request for name {}", name);
        return ResponseEntity.ok(
                departmentService.getDepartmentByName(name)
        );
    }

    @GetMapping("/code")
    public ResponseEntity<DepartmentResponse> getDepartmentByCode(
            @RequestParam String code) {
        logger.info("Received GET /departments/code request for code {}",code);
        return ResponseEntity.ok(
                departmentService.getDepartmentByCode(code)
        );
    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable String departmentId,
            @Valid @RequestBody DepartmentRequest request) {
        logger.info("Received PATCH /departments/{} request", departmentId);
        return ResponseEntity.ok(
                departmentService.updateDepartment(departmentId, request)
        );
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable String departmentId) {
        logger.info("Received DELETE /departments/{} request",departmentId);
        departmentService.deleteDepartmentById(departmentId);

        return ResponseEntity.noContent().build();
    }
}