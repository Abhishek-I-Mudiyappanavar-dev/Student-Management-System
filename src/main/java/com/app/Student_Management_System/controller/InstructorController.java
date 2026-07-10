package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.request.InstructorUpdateRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.enums.Designation;
import com.app.Student_Management_System.service.InstructorService;
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
@RequestMapping("/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructors",
description = "Operations for managing instructor records")
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    @Operation(
            summary = "Get all instructors",
            description = """
                Returns all instructors registered in the system.
                Returns an empty list if no instructors are available.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructors returned successfully",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = InstructorResponse.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<InstructorResponse>> getAllInstructors(){

        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @GetMapping("/{instructorId}")
    @Operation(
            summary = "Get instructor by ID",
            description = """
                Returns the details of the instructor identified by the specified instructor ID.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructor returned successfully",
                    content = @Content(
                            schema = @Schema(implementation = InstructorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Instructor not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<InstructorResponse> getInstructorById(@PathVariable String instructorId){

        return ResponseEntity.ok(instructorService.getInstructorById(instructorId));
    }

    @GetMapping("/name")
    @Operation(
            summary = "Get instructors by name",
            description = """
                Returns all instructors whose name matches the specified value.
                Returns an empty list if no matching instructors are found.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructors returned successfully",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = InstructorResponse.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<InstructorResponse>> getInstructorsByName(@RequestParam String name){

        return ResponseEntity.ok(instructorService.getInstructorsByName(name));
    }

    @GetMapping("/designation")
    @Operation(
            summary = "Get instructors by designation",
            description = """
                Returns all instructors having the specified designation.
                Returns an empty list if no instructors match the designation.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructors returned successfully",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = InstructorResponse.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<InstructorResponse>> getInstructorsByDesignation(@RequestParam Designation designation){

        return ResponseEntity.ok(instructorService.getInstructorByDesignation(designation));
    }

    @GetMapping("/email")
    @Operation(
            summary = "Get instructor by email",
            description = """
                Returns the details of the instructor associated with the specified email address.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructor returned successfully",
                    content = @Content(
                            schema = @Schema(implementation = InstructorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Instructor not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<InstructorResponse> getInstructorByEmail(@RequestParam String email){

        return ResponseEntity.ok(instructorService.getInstructorByEmail(email));
    }

    @PostMapping
    @Operation(
            summary = "Create a new instructor",
            description = """
                Creates a new instructor record. The request is validated before processing.
                The instructor's email must be unique, and the specified department must already exist.
                Returns the created instructor's details upon successful creation.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Instructor created successfully",
                    content = @Content(
                            schema = @Schema(implementation = InstructorResponse.class)
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
                    description = "Specified department not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "An instructor with the provided email already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.createInstructor(request));
    }

    @PatchMapping("/{instructorId}")
    @Operation(
            summary = "Update instructor information",
            description = """
                Updates one or more instructor attributes.
                Only the fields provided in the request are modified.
                If the email is updated, it must remain unique across all instructors.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructor updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = InstructorResponse.class)
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
                    description = "Instructor not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The provided email is already associated with another instructor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<InstructorResponse> updateInstructor(@Valid @RequestBody InstructorUpdateRequest request, @PathVariable String instructorId){

        return ResponseEntity.ok(instructorService.updateInstructor(instructorId, request));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(
            summary = "Get instructors by department",
            description = """
                Returns all instructors belonging to the specified department.
                Returns an empty list if the department exists but has no instructors.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructors returned successfully",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = InstructorResponse.class)
                            )
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
    public ResponseEntity<List<InstructorResponse>> getInstructorsByDepartment(@PathVariable String departmentId){

        return ResponseEntity.ok(instructorService.getInstructorsByDepartmentId(departmentId));
    }

    @DeleteMapping("/{instructorId}")
    @Operation(
            summary = "Delete an instructor",
            description = """
                Deletes the instructor identified by the specified instructor ID.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Instructor deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Instructor not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteInstructor(@PathVariable String instructorId){

        instructorService.deleteInstructorById(instructorId);
        return ResponseEntity.noContent().build();
    }
}
