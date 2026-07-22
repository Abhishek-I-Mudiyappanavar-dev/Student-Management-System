package com.app.Student_Management_System.dto.request;

import com.app.Student_Management_System.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EnrollmentUpdateRequest {

    @NotNull
    private EnrollmentStatus status;
}
