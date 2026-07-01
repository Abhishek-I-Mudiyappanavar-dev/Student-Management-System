package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    Optional<Student> findByEmail(String email);

    List<Student> getStudentByDepartmentId(String departmentId);
}
