package com.app.Student_Management_System.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CourseResponse {

    private String id;

    private String title;

    private String code;

    private Integer credits;

    private String description;

    private String instructorId;

    private String instructorName;

    private String departmentId;

    private String departmentName;

}
