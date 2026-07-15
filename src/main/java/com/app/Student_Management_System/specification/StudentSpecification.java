package com.app.Student_Management_System.specification;

import com.app.Student_Management_System.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public final class StudentSpecification {

    private StudentSpecification(){

    }

    public static Specification<Student> hasFirstName(String firstName){

        if(firstName==null || firstName.isBlank()){
            return Specification.unrestricted();
        }
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("firstName"), firstName)
        );
    }

    public static Specification<Student> hasLastName(String lastName){
        if(lastName==null || lastName.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastName"), lastName)
        );
    }

    public static Specification<Student> hasDepartmentId(String departmentId){
        if(departmentId==null || departmentId.isBlank()) return Specification.unrestricted();

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("department").get("id"), departmentId));
    }
}
