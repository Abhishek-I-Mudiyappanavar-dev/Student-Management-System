package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, String >, JpaSpecificationExecutor<Instructor> {

    Optional<Instructor> findByEmail(String email);

}
