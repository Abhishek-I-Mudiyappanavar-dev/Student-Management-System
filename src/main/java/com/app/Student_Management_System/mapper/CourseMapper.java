package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.request.CourseRequest;
import com.app.Student_Management_System.dto.response.CourseResponse;
import com.app.Student_Management_System.entity.Course;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Instructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request, Department department, Instructor instructor){

        Course course = new Course();
        course.setCode(request.getCode());
        course.setTitle(request.getTitle());
        course.setCredits(request.getCredits());
        course.setDescription(request.getDescription());
        course.setDepartment(department);
        course.setInstructor(instructor);

        return course;
    }

    public CourseResponse toResponse(Course course){

        CourseResponse response = new CourseResponse();

        response.setId(course.getId());
        response.setCode(course.getCode());
        response.setCredits(course.getCredits());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setDepartmentId(course.getDepartment().getId());
        response.setDepartmentName(course.getDepartment().getName());
        Instructor instructor = course.getInstructor();
        if(instructor!=null){
            response.setInstructorId(instructor.getId());
            response.setInstructorName(instructor.getName());
        }else{
            response.setInstructorId(null);
            response.setInstructorName(null);
        }

        return response;
    }

    public List<CourseResponse> toResponseList(List<Course> courses){
        return courses.stream()
                .map(this::toResponse)
                .toList();
    }
}
