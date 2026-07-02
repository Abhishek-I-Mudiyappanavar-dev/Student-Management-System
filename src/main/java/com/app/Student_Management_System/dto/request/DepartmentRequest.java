package com.app.Student_Management_System.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String description;
}
