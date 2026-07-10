package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Schema(
        description = """
        Request payload for partially updating a student.
        Only the fields provided in the request will be updated.
        Fields omitted or set to null will remain unchanged.
        """
)
public class StudentUpdateRequest {

    @Schema(
            description = "Updated first name of the student. Leave null to keep the current value.",
            example = "Abhishek"
    )
    private String firstName;

    @Schema(
            description = "Updated last name of the student. Leave null to keep the current value.",
            example = "Mudiyappanavar"
    )
    private String lastName;

    @Schema(
            description = "Updated email address. Must be unique if provided.",
            example = "abhishek@example.com",
            format = "email"
    )
    @Email
    private String email;

    @Schema(
            description = "Updated phone number of the student.",
            example = "9876543210"
    )
    private String phoneNumber;

    @Schema(
            description = "Updated gender of the student.",
            example = "MALE",
            allowableValues = {
                    "MALE",
                    "FEMALE",
                    "OTHER"
            }
    )
    private Gender gender;
}