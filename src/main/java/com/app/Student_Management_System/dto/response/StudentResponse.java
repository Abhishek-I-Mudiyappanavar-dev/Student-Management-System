package com.app.Student_Management_System.dto.response;

import com.app.Student_Management_System.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private String phoneNumber;

    private Integer earnedCredits;

    private LocalDate dateOfBirth;

    private String profilePicturePath;

    private String departmentId;

    private String departmentName;

}
