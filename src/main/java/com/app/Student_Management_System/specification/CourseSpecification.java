package com.app.Student_Management_System.specification;

import com.app.Student_Management_System.entity.Course;
import org.springframework.data.jpa.domain.Specification;

public final class CourseSpecification {

    private CourseSpecification(){

    }

    public static Specification<Course> hasTitle(String title){
        if(title==null || title.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%"+ title.toLowerCase() + "%"));
    }

    public static Specification<Course> hasInstructorId(String instructorId){
        if(instructorId==null || instructorId.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("instructor").get("id"), instructorId));
    }

    public static Specification<Course> hasDepartmentId(String departmentId){
        if(departmentId==null || departmentId.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("department").get("id"), departmentId));
    }

    public static Specification<Course> hasCredits(Integer credits){
        if(credits==null) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("credits"), credits));
    }
}
