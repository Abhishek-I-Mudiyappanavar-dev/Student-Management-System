package com.app.Student_Management_System.repository;

import com.app.Student_Management_System.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {

}
