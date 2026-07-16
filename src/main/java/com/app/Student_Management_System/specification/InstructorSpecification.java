package com.app.Student_Management_System.specification;

import com.app.Student_Management_System.entity.Instructor;
import com.app.Student_Management_System.enums.Designation;
import org.springframework.data.jpa.domain.Specification;

public class InstructorSpecification {
    private InstructorSpecification(){

    }

    public static Specification<Instructor> hasName(String name){
        if(name==null || name.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name));
    }

    public static Specification<Instructor> hasDepartmentId(String departmentId){
        if(departmentId==null || departmentId.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("department").get("id"), departmentId));
    }

    public static Specification<Instructor> hasDesignation(Designation designationEnum){
        if (designationEnum==null) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("designation"), designationEnum));
    }
}
