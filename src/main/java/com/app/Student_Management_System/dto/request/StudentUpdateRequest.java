package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Gender;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class StudentUpdateRequest {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private Gender gender;
}
