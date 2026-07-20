package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.CourseRequest;
import com.app.Student_Management_System.dto.request.CourseUpdateRequest;
import com.app.Student_Management_System.dto.response.CourseResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.entity.Course;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Instructor;
import com.app.Student_Management_System.exception.ResourceNotFoundException;
import com.app.Student_Management_System.mapper.CourseMapper;
import com.app.Student_Management_System.mapper.PageResponseMapper;
import com.app.Student_Management_System.repository.CourseRepository;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.repository.InstructorRepository;
import com.app.Student_Management_System.service.CourseService;
import com.app.Student_Management_System.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final DepartmentRepository departmentRepository;

    private final InstructorRepository instructorRepository;

    private final CourseMapper courseMapper;

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Override
    public PageResponse<CourseResponse> searchCourses(
            String title,Integer credits, String departmentId, String instructorId, Pageable pageable) {

        Specification<Course> specification = Specification.allOf(
                CourseSpecification.hasTitle(title),
                CourseSpecification.hasInstructorId(instructorId),
                CourseSpecification.hasDepartmentId(departmentId),
                CourseSpecification.hasCredits(credits)
        );

        Page<Course> courses = courseRepository.findAll(specification, pageable);
        List<CourseResponse> responses = courseMapper.toResponseList(courses.getContent());
        return PageResponseMapper.toPageResponse(courses, responses);
    }

    @Override
    public CourseResponse getCourseById(String courseId) {
        Course course = getCourseOrThrow(courseId);
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse getCourseByCode(String code) {
        Course course = courseRepository.findByCode(code).orElseThrow(() ->{
            logger.warn("Course Not found with code '{}'", code);
            return new ResourceNotFoundException("Course Not found with code: "+code);
        });
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse addCourse(CourseRequest request) {

        logger.info(
                "Course creation process started for code '{}' and title '{}'",
                request.getCode(),
                request.getTitle()
        );

        Department department = getDepartmentOrThrow(request.getDepartmentId());

        Instructor instructor = null;
        if(request.getInstructorId()!=null && !request.getInstructorId().isBlank()){
             instructor = getInstructorOrThrow(request.getInstructorId());
            if(!Objects.equals(department.getId(), instructor.getDepartment().getId())){
                logger.warn(
                        "Course creation rejected: instructor department '{}' does not match course department '{}'.",
                        instructor.getDepartment().getCode(),
                        department.getCode()
                );
                throw new IllegalStateException("Instructor should be from the department which the course been introduced");
            }
        }

        if(courseRepository.existsByCode(request.getCode())){
            logger.warn(
                    "Course creation rejected: code '{}' already exists.",
                    request.getCode()
            );
            throw new IllegalStateException("code: "+request.getCode()+ " already associated with other course");
        }

        Course course = courseMapper.toEntity(request, department, instructor);
        CourseResponse response = courseMapper.toResponse(courseRepository.save(course));
        logger.info(
                "Course created successfully with id '{}' and code '{}'",
                course.getId(),
                course.getCode()
        );
        return response;
    }

    @Override
    public CourseResponse updateCourse(String courseId, CourseUpdateRequest request) {

        logger.info("Processing update request for course with id '{}'", courseId);

        Course course = getCourseOrThrow(courseId);

        if(request.getTitle()!=null && !request.getTitle().isBlank()){
            course.setTitle(request.getTitle());
        }
        if(request.getInstructorId()!=null && !request.getInstructorId().isBlank()){
            Instructor instructor = getInstructorOrThrow(request.getInstructorId());

            if(!Objects.equals(course.getDepartment().getId(), instructor.getDepartment().getId())){
                logger.warn(
                        "Instructor assignment rejected: instructor department '{}' does not match course department '{}'.",
                        instructor.getDepartment().getCode(),
                        course.getDepartment().getCode()
                );
                throw new IllegalStateException(
                        String.format(
                                "Instructor department '%s' does not match course department '%s'.",
                                instructor.getDepartment().getCode(),
                                course.getDepartment().getCode()
                        )
                );
            }
            course.setInstructor(instructor);
        }
        if(request.getDescription()!=null && !request.getDescription().isBlank()){
            course.setDescription(request.getDescription());
        }
        course = courseRepository.save(course);
        logger.info("Course updated successfully bearing id: '{}'", courseId);
        return courseMapper.toResponse(course);
    }

    @Override
    public void deleteCourseById(String courseId) {

        logger.info("Processing delete request for course with id '{}'", courseId);

        courseRepository.delete(getCourseOrThrow(courseId));
        logger.info("Course deleted successfully bearing id: '{}' ", courseId);
    }

    private Course getCourseOrThrow(String courseId){
        return courseRepository.findById(courseId).orElseThrow(()->{
            logger.warn("Course Not found with id '{}'", courseId);
            return new ResourceNotFoundException("Course Not found with id: "+courseId);
        });
    }

    private Department getDepartmentOrThrow(String departmentId){
        return departmentRepository.findById(departmentId).orElseThrow(()-> {
            logger.warn("Department Not found with id '{}'", departmentId);
            return new ResourceNotFoundException("Department Not found with id "+departmentId);
        });
    }

    private Instructor getInstructorOrThrow(String instructorId){
        return instructorRepository.findById(instructorId).orElseThrow(()-> {
            logger.warn("Instructor Not found with id '{}'", instructorId);
            return new ResourceNotFoundException("Instructor Not found with id "+instructorId);
        });
    }
}
