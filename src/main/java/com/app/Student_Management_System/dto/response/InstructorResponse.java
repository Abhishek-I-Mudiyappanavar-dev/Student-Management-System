package com.app.Student_Management_System.dto.response;

import com.app.Student_Management_System.enums.Designation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstructorResponse {

    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private Designation designation;

    private String departmentId;
}
