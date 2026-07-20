package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.dto.response.CourseResponse;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {

    Optional<Course> findByCode(String code);

    boolean existsByCode(String code);

}
