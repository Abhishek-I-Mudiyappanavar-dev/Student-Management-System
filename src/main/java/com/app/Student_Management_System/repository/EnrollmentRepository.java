package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String>, JpaSpecificationExecutor<Enrollment> {

}
