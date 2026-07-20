package com.app.Student_Management_System.specification;

import com.app.Student_Management_System.entity.Instructor;
import com.app.Student_Management_System.enums.Designation;
import org.springframework.data.jpa.domain.Specification;

public final class InstructorSpecification {
    private InstructorSpecification(){

    }

    public static Specification<Instructor> hasName(String name){
        if(name==null || name.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%"+name.toLowerCase()+"%"));
    }

    public static Specification<Instructor> hasDepartmentId(String departmentId){
        if(departmentId==null || departmentId.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("department").get("id"), departmentId));
    }

    public static Specification<Instructor> hasDesignation(Designation designationEnum){
        if (designationEnum==null) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("designation"), designationEnum));
    }
}
