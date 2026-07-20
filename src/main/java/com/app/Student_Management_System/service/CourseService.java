package com.app.Student_Management_System.service;

import com.app.Student_Management_System.dto.request.CourseRequest;
import com.app.Student_Management_System.dto.request.CourseUpdateRequest;
import com.app.Student_Management_System.dto.response.CourseResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    PageResponse<CourseResponse> searchCourses(String title,Integer credits, String departmentId, String instructorId, Pageable pageable);

    CourseResponse getCourseById(String courseId);

    CourseResponse getCourseByCode(String code);

    CourseResponse addCourse(CourseRequest request);

    CourseResponse updateCourse(String courseId, CourseUpdateRequest request);

    void deleteCourseById(String courseId);

}
