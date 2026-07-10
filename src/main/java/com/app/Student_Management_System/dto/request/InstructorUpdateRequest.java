package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.Designation;
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
                Request payload for partially updating an existing instructor.
                Only the fields provided in the request will be updated.
                Fields omitted or set to null will remain unchanged.
                """
)
public class InstructorUpdateRequest {

    @Schema(
            description = "Updated full name of the instructor. Leave null to keep the current value.",
            example = "Dr. Jane Smith"
    )
    private String name;

    @Schema(
            description = "Updated academic designation of the instructor.",
            example = "ASSOCIATE_PROFESSOR"
    )
    private Designation designation;

    @Schema(
            description = "Updated email address of the instructor. Must be unique if provided.",
            example = "jane.smith@university.edu",
            format = "email"
    )
    @Email
    private String email;

    @Schema(
            description = "Updated contact phone number of the instructor.",
            example = "9876543210"
    )
    private String phoneNumber;
}