package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.EnrollmentRequest;
import com.app.Student_Management_System.dto.request.EnrollmentUpdateRequest;
import com.app.Student_Management_System.dto.response.EnrollmentResponse;
import com.app.Student_Management_System.dto.response.ErrorResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.enums.EnrollmentStatus;
import com.app.Student_Management_System.enums.Semester;
import com.app.Student_Management_System.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Tag(
        name = "Enrollments",
        description = "Operations for managing student course enrollments"
)
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @Operation(
            summary = "Search enrollments",
            description = """
                    Retrieves enrollments matching the supplied filters.
                    If multiple filters are provided, only enrollments satisfying all criteria are returned.
                    If no filters are provided, all enrollments are returned.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollments retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = PageResponse.class)
                    )
            )
    })
    @PageableAsQueryParam
    public ResponseEntity<PageResponse<EnrollmentResponse>> searchEnrollments(
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) String departmentCode,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) EnrollmentStatus status,
            @RequestParam(required = false) String courseTitle,
            @RequestParam(required = false) Semester semester,
            @PageableDefault Pageable pageable) {

        return ResponseEntity.ok(
                enrollmentService.search(
                        studentId,
                        courseCode,
                        semester,
                        courseTitle,
                        status,
                        academicYear,
                        departmentCode,
                        pageable
                )
        );
    }

    @GetMapping("/{enrollmentId}")
    @Operation(
            summary = "Get enrollment by ID",
            description = """
                    Returns the enrollment identified by the specified enrollment ID.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = EnrollmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(
            @PathVariable String enrollmentId) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentById(enrollmentId)
        );
    }

    @PostMapping
    @Operation(
            summary = "Enroll a student in a course",
            description = """
                    Creates a new enrollment for a student in the specified course.
                    The enrollment status is automatically initialized as ACTIVE.
                    Duplicate enrollments for the same student and course are not permitted.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Enrollment created successfully",
                    content = @Content(
                            schema = @Schema(implementation = EnrollmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student or Course not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Enrollment already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentResponse> enrollCourse(
            @Valid @RequestBody EnrollmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentService.enrollCourse(request));
    }

    @PatchMapping("/{enrollmentId}")
    @Operation(
            summary = "Update an enrollment",
            description = """
                    Updates the enrollment identified by the specified enrollment ID.
                    Only the supplied fields are modified.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = EnrollmentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentResponse> updateEnrolledCourse(
            @PathVariable String enrollmentId,
            @Valid @RequestBody EnrollmentUpdateRequest updateRequest) {

        return ResponseEntity.ok(
                enrollmentService.updateEnrollment(enrollmentId, updateRequest)
        );
    }

    @DeleteMapping("/{enrollmentId}")
    @Operation(
            summary = "Delete an enrollment",
            description = """
                    Deletes the enrollment identified by the specified enrollment ID.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Enrollment deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable String enrollmentId) {

        enrollmentService.deleteEnrollment(enrollmentId);

        return ResponseEntity.noContent().build();
    }
}