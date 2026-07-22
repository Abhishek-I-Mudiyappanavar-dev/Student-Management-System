package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.EnrollmentRequest;
import com.app.Student_Management_System.dto.request.EnrollmentUpdateRequest;
import com.app.Student_Management_System.dto.response.EnrollmentResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.entity.Course;
import com.app.Student_Management_System.entity.Enrollment;
import com.app.Student_Management_System.entity.Student;
import com.app.Student_Management_System.enums.EnrollmentStatus;
import com.app.Student_Management_System.enums.Semester;
import com.app.Student_Management_System.exception.ResourceNotFoundException;
import com.app.Student_Management_System.mapper.EnrollmentMapper;
import com.app.Student_Management_System.mapper.PageResponseMapper;
import com.app.Student_Management_System.repository.*;
import com.app.Student_Management_System.service.EnrollmentService;
import com.app.Student_Management_System.specification.EnrollmentSpecification;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final EnrollmentMapper enrollmentMapper;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    @Override
    public PageResponse<EnrollmentResponse> search
            (String studentId, String courseCode, Semester semester, String courseTitle, EnrollmentStatus status,
             String academicYear, String departmentCode, Pageable pageable) {

        Specification<Enrollment> specifications = Specification.allOf(
                EnrollmentSpecification.hasStudentId(studentId),
                EnrollmentSpecification.hasAcademicYear(academicYear),
                EnrollmentSpecification.hasCourseCode(courseCode),
                EnrollmentSpecification.hasCourseTitle(courseTitle),
                EnrollmentSpecification.hasStatus(status),
                EnrollmentSpecification.hasSemester(semester),
                EnrollmentSpecification.hasDepartment(departmentCode)
        );

        Page<Enrollment> enrollments = enrollmentRepository.findAll(specifications, pageable);

        List<EnrollmentResponse> responses = enrollmentMapper.toResponseList(enrollments.getContent());
        return PageResponseMapper.toPageResponse(enrollments, responses);
    }

    @Override
    public EnrollmentResponse enrollCourse(EnrollmentRequest request) {

        logger.info(
                "Creating enrollment for student '{}' in course '{}'.",
                request.getStudentId(),
                request.getCourseId()
        );

         Student student = studentRepository.findById(request.getStudentId()).orElseThrow(()->{
             logger.warn("Student Not found with id '{}'", request.getStudentId());
             return new ResourceNotFoundException("Student Not found with id: " + request.getStudentId());
         });

         Course course = courseRepository.findById(request.getCourseId()).orElseThrow(()->{
             logger.warn("Course Not found with id '{}'", request.getCourseId());
             return new ResourceNotFoundException("Course Not found with id: "+request.getCourseId());
         });

         if(enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())){
             throw new DuplicateRequestException(String.format(
                     "Enrollment already exists with studentId: '%s' and courseId: '%s'",request.getStudentId(),request.getCourseId()));
         }

        Enrollment enrollment = enrollmentMapper.toEntity(request, course, student);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment = enrollmentRepository.save(enrollment);
        logger.info(
                "Enrollment '{}' created successfully.",
                enrollment.getId()
        );
        return enrollmentMapper.toResponse(enrollment);
    }

    @Override
    public EnrollmentResponse updateEnrollment(String enrollmentId, EnrollmentUpdateRequest request) {

        logger.info("Updating enrollment with id '{}'.", enrollmentId);

        Enrollment enrollment = getEnrollmentOrThrow(enrollmentId);
        enrollment.setStatus(request.getStatus());
        enrollment = enrollmentRepository.save(enrollment);
        logger.info(
                "Enrollment '{}' updated successfully.",
                enrollment.getId()
        );
        return enrollmentMapper.toResponse(enrollment);
    }

    @Override
    public EnrollmentResponse getEnrollmentById(String enrollmentId) {
        return enrollmentMapper.toResponse(getEnrollmentOrThrow(enrollmentId));
    }

    @Override
    public void deleteEnrollment(String enrollmentId) {
        logger.info("Deleting enrollment with id '{}'.", enrollmentId);
        enrollmentRepository.delete(getEnrollmentOrThrow(enrollmentId));
        logger.info("Enrollment '{}' deleted successfully.", enrollmentId);
    }

    private Enrollment getEnrollmentOrThrow(String enrollmentId){
        return enrollmentRepository.findById(enrollmentId).orElseThrow(()->{
            logger.warn("Enrollment Not found with id '{}'", enrollmentId);
            return new ResourceNotFoundException("Enrollment Not found with id: "+ enrollmentId);
        });
    }
}
