package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.DepartmentRequest;
import com.app.Student_Management_System.dto.response.DepartmentResponse;
import com.app.Student_Management_System.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.Student_Management_System.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "Departments",
description = "Operations for managing department records")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @Operation(
            summary = "Create a new department",
            description = """
                Creates a new department record.
                The department name and code must be unique.
                Returns the created department upon successful creation.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Department created successfully",
                    content = @Content(
                            schema = @Schema(implementation = DepartmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or validation failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Department name or code already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response =
                departmentService.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all departments",
            description = """
                Returns all departments registered in the system.
                Returns an empty list if no departments are available.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departments returned successfully",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = DepartmentResponse.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get department by ID",
            description = """
                Returns the department identified by the specified department ID.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Department returned successfully",
                    content = @Content(
                            schema = @Schema(implementation = DepartmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id)
        );
    }

    @GetMapping("/name")
    @Operation(
            summary = "Get department by name",
            description = """
                Returns the department associated with the specified department name.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Department returned successfully",
                    content = @Content(
                            schema = @Schema(implementation = DepartmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<DepartmentResponse> getDepartmentByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                departmentService.getDepartmentByName(name)
        );
    }

    @GetMapping("/code")
    @Operation(
            summary = "Get department by code",
            description = """
                Returns the department associated with the specified department code.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Department returned successfully",
                    content = @Content(
                            schema = @Schema(implementation = DepartmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<DepartmentResponse> getDepartmentByCode(
            @RequestParam String code) {

        return ResponseEntity.ok(
                departmentService.getDepartmentByCode(code)
        );
    }

    @PatchMapping("/{departmentId}")
    @Operation(
            summary = "Update a department",
            description = """
                Updates the department identified by the specified department ID.
                The department name and code must remain unique across the system.
                Returns the updated department upon successful completion.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Department updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = DepartmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or validation failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Department name or code already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable String departmentId,
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(departmentId, request)
        );
    }

    @DeleteMapping("/{departmentId}")
    @Operation(
            summary = "Delete a department",
            description = """
                Deletes the department identified by the specified department ID.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Department deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable String departmentId) {

        departmentService.deleteDepartmentById(departmentId);

        return ResponseEntity.noContent().build();
    }
}