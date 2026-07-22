package com.app.Student_Management_System.specification;

import com.app.Student_Management_System.entity.Enrollment;
import com.app.Student_Management_System.enums.EnrollmentStatus;
import com.app.Student_Management_System.enums.Semester;
import org.springframework.data.jpa.domain.Specification;

public final class EnrollmentSpecification {

    private EnrollmentSpecification(){

    }

    public static Specification<Enrollment> hasStudentId(String studentId){
        if(studentId==null || studentId.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("student").get("id"), studentId));
    }

    public static Specification<Enrollment> hasCourseCode(String courseCode){
        if(courseCode==null || courseCode.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("course").get("code"), courseCode));
    }

    public static Specification<Enrollment> hasCourseTitle(String courseTitle){
        if(courseTitle==null || courseTitle.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.join("course").get("title")),
                        "%"+ courseTitle.toLowerCase() +"%"));
    }

    public static Specification<Enrollment> hasAcademicYear(String academicYear){
        if(academicYear==null || academicYear.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("academicYear"), academicYear));
    }

    public static Specification<Enrollment> hasStatus(EnrollmentStatus status){
        if(status==null) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status));
    }

    public static Specification<Enrollment> hasSemester(Semester semester){
        if(semester==null) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("semester"), semester));
    }

    public static Specification<Enrollment> hasDepartment(String departmentCode){
        if(departmentCode ==null || departmentCode.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal
                        (root.join("course").join("department").get("code"), departmentCode));
    }
}
