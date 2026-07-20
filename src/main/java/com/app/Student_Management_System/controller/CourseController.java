package com.app.Student_Management_System.controller;

import com.app.Student_Management_System.dto.request.CourseRequest;
import com.app.Student_Management_System.dto.request.CourseUpdateRequest;
import com.app.Student_Management_System.dto.response.CourseResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.service.CourseService;
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
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course", description = "APIs for managing courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(
            summary = "Search courses",
            description = """
                    Retrieves courses matching the supplied filters.
                    If multiple filters are provided, only courses satisfying all criteria are returned.
                    If no filters are provided, all courses are returned.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Courses retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = PageResponse.class)
                    )
            )
    })
    @PageableAsQueryParam
    public ResponseEntity<PageResponse<CourseResponse>> searchCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer credits,
            @RequestParam(required = false) String instructorId,
            @RequestParam(required = false) String departmentId,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(
                courseService.searchCourses(
                        title,
                        credits,
                        departmentId,
                        instructorId,
                        pageable
                )
        );
    }

    @GetMapping("/{courseId}")
    @Operation(
            summary = "Get course by ID",
            description = "Retrieves a course using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = CourseResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponse> getCourseById(
            @PathVariable String courseId) {

        return ResponseEntity.ok(
                courseService.getCourseById(courseId)
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a new course",
            description = "Creates a new course and associates it with the specified department. An instructor may optionally be assigned during course creation."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Course created successfully",
                    content = @Content(
                            schema = @Schema(implementation = CourseResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Department or Instructor not found"),
            @ApiResponse(responseCode = "409", description = "Course code already exists")
    })
    public ResponseEntity<CourseResponse> addCourse(
            @Valid @RequestBody CourseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.addCourse(request));
    }

    @PatchMapping("/{courseId}")
    @Operation(
            summary = "Update course details",
            description = "Updates the provided course details. Only the supplied fields are modified."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = CourseResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Course or Instructor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update request")
    })
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable String courseId,
            @Valid @RequestBody CourseUpdateRequest request) {

        return ResponseEntity.ok(
                courseService.updateCourse(courseId, request)
        );
    }

    @DeleteMapping("/{courseId}")
    @Operation(
            summary = "Delete a course",
            description = "Deletes the course identified by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> deleteCourse(
            @PathVariable String courseId) {

        courseService.deleteCourseById(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code")
    @Operation(
            summary = "Get course by code",
            description = "Retrieves a course using its unique course code."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = CourseResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponse> getCourseByCode(
            @RequestParam String code) {

        return ResponseEntity.ok(
                courseService.getCourseByCode(code)
        );
    }
}