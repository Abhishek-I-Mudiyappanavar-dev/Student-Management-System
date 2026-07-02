package com.app.Student_Management_System.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DepartmentResponse {

    private String id;

    private String name;

    private String code;

    private String description;
}
