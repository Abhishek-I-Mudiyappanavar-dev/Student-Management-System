package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.request.StudentUpdateRequest;
import com.app.Student_Management_System.dto.response.ErrorResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.dto.response.StudentResponse;
import com.app.Student_Management_System.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Students",
description = "Operations for managing student records")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation( summary = "Create a new student",
            description = """
                Creates a new student record. The request is validated before processing.
                The student's email must be unique, and the specified department must already exist.
                Returns the created student's details upon successful creation.
                """)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(
                            schema = @Schema(implementation = StudentResponse.class)
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
                    description = "A student with the provided email already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> admitStudent(
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response = studentService.admitStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @Operation(
            summary = "Search students",
            description = """
                    Retrieves students matching the supplied filters.
                    If multiple filters are provided, only students satisfying all criteria are returned.
                    If no filters are provided, all students are returned.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Students retrieved successfully",
                    content = @Content( array = @ArraySchema(
                            schema = @Schema(implementation = PageResponse.class)
                    ))
            )
    })
    @PageableAsQueryParam
    public ResponseEntity<PageResponse<StudentResponse>> searchStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String departmentId,
            @PageableDefault Pageable pageable) {

        return ResponseEntity.ok(
                studentService.searchStudents(firstName, lastName, departmentId , pageable)
        );
    }

    @GetMapping("/{studentId}")
    @Operation(
            summary = "Get student by ID",
            description = """
                Retrieves the details of a student using the student's unique identifier.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student returned successfully",
                    content = @Content(
                            schema = @Schema(implementation = StudentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable String studentId) {

        return ResponseEntity.ok(
                studentService.getStudentById(studentId)
        );
    }

    @GetMapping("/email")
    @Operation(
            summary = "Get student by email",
            description = """
                Retrieves a student's details using the student's registered email address.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = StudentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> getStudentByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                studentService.getStudentByEmail(email)
        );
    }

    @GetMapping("/earnedCredits/{studentId}")
    @Operation(
            summary = "Get earned credits",
            description = """
                Retrieves the total earned academic credits for the specified student.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Earned credits retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = Integer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Integer> getStudentEarnedCredits(@PathVariable String studentId){

        return ResponseEntity.ok(studentService.getStudentEarnedCredits(studentId));
    }


    @PatchMapping("/{studentId}")
    @Operation(
            summary = "Update student information",
            description = """
                Updates one or more student attributes.
                Only the fields provided in the request are modified.
                If the email is updated, it must remain unique across all students.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = StudentResponse.class)
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
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The provided email is already associated with another student",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable String studentId,
            @Valid @RequestBody StudentUpdateRequest request) {

        return ResponseEntity.ok(
                studentService.updateStudent(studentId, request)
        );
    }


    @PostMapping(value = "/{studentId}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update student profile picture",
            description = """
                Updates the profile picture of the student identified by the specified ID.
                Accepts an image file as multipart/form-data and replaces the existing profile picture.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile picture updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = StudentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid image file or request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> updateProfilePictureById(@PathVariable String studentId, @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(studentService.uploadProfilePicture(studentId, file));
    }


    @DeleteMapping("/{studentId}")
    @Operation(
            summary = "Delete a student",
            description = """
                Deletes the student identified by the specified student ID.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Student deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteStudent(
            @PathVariable String studentId) {

        studentService.deleteStudent(studentId);

        return ResponseEntity.noContent().build();
    }
}