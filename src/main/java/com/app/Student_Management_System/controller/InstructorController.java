package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.InstructorRequest;
import com.app.Student_Management_System.dto.request.InstructorUpdateRequest;
import com.app.Student_Management_System.dto.response.InstructorResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.enums.Designation;
import com.app.Student_Management_System.service.InstructorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructors",
description = "Operations for managing instructor records")
public class InstructorController {

    private final InstructorService instructorService;

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

    @GetMapping
    @Operation(
            summary = "Search instructors",
            description = """
                    Retrieves instructors matching the supplied filters.
                    If multiple filters are provided, only instructors satisfying all criteria are returned.
                    If no filters are provided, all instructors are returned.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Instructors retrieved successfully",
                    content = @Content( array = @ArraySchema(
                            schema = @Schema(implementation = PageResponse.class)
                    ))
            )
    })
    @PageableAsQueryParam
    public ResponseEntity<PageResponse<InstructorResponse>> searchInstructors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) Designation designation ,
            @PageableDefault Pageable pageable){
        return ResponseEntity.ok(instructorService.searchInstructors(
                name, departmentId, designation, pageable
        ));
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
    public ResponseEntity<InstructorResponse> appointInstructor(@Valid @RequestBody InstructorRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.appointInstructor(request));
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
