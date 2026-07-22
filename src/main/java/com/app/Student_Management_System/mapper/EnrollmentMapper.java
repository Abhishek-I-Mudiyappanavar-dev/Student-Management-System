package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.request.EnrollmentRequest;
import com.app.Student_Management_System.dto.response.EnrollmentResponse;
import com.app.Student_Management_System.entity.Course;
import com.app.Student_Management_System.entity.Enrollment;
import com.app.Student_Management_System.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentMapper {

    public Enrollment toEntity(EnrollmentRequest request, Course course, Student student){

        Enrollment enrollment = new Enrollment();
        enrollment.setAcademicYear(request.getAcademicYear());
        enrollment.setCourse(course);
//        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setSemester(request.getSemester());
        enrollment.setStudent(student);

        return enrollment;
    }

    public EnrollmentResponse toResponse(Enrollment enrollment){

        Course course = enrollment.getCourse();
        Student student = enrollment.getStudent();

        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setSemester(enrollment.getSemester());
        response.setStatus(enrollment.getStatus());
        response.setAcademicYear(enrollment.getAcademicYear());
        response.setEnrollmentDate(enrollment.getEnrollmentDate());
        response.setCourseCode(course.getCode());
        response.setCourseId(course.getId());
        response.setCourseTitle(course.getTitle());
        response.setStudentId(student.getId());
        response.setStudentName(student.getFirstName());

        return response;
    }

    public List<EnrollmentResponse> toResponseList(List<Enrollment> enrollments){
        return enrollments.stream()
                .map(this::toResponse)
                .toList();
    }

}
