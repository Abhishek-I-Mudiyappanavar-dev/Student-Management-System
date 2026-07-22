package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String>, JpaSpecificationExecutor<Enrollment> {

    boolean existsByStudentIdAndCourseId(String studentId, String courseId);

    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, String courseId);
}
