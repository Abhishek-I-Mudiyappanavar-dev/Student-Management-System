package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Instructor;
import com.app.Student_Management_System.enums.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, String >, JpaSpecificationExecutor<Instructor> {

    List<Instructor> findByName(String name);

    Optional<Instructor> findByEmail(String email);

    List<Instructor> findByDesignation(Designation designation);

    List<Instructor> findByDepartmentId(String id);
}
