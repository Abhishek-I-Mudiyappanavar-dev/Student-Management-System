package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    Page<Student> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    Page<Student> findByFirstName(String firstName, Pageable pageable);

    Page<Student> findByLastName(String lastName, Pageable pageable);

    Optional<Student> findByEmail(String email);

    List<Student> findByDepartmentId(String departmentId);

}
