package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Designation;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class InstructorUpdateRequest {

    private String name;

    private Designation designation;

    @Email
    private String email;

    private String phoneNumber;
}
