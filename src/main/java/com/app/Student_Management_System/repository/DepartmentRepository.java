package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String > {

    Optional<Department> findByCode(String code);

    Page<Department> findByName(String name);

    Boolean existsByCode(String code);

    Boolean existsByName(String name);

}
