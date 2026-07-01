# Student Management System (Enterprise Backend)

An enterprise-style REST API built with Spring Boot that manages students, departments, courses, instructors, and enrollments. The project focuses on designing scalable backend architecture rather than just implementing CRUD operations.

Throughout the project, I will build production-oriented APIs while learning core backend concepts such as layered architecture, DTO design, JPA/Hibernate relationships, transaction management, validation, exception handling, pagination, filtering, sorting, searching, logging, API documentation, and unit testing.

## Features

* Student Management (CRUD)
* Department Management
* Course Management
* Instructor Management
* Student Enrollment System
* Advanced Search & Filtering
* Pagination & Sorting
* Input Validation
* Global Exception Handling
* API Documentation (Swagger/OpenAPI)
* Logging with SLF4J & AOP
* Unit & Integration Testing

## Tech Stack

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok
* Bean Validation
* Swagger/OpenAPI
* JUnit & Mockito

## Learning Objectives

* Design enterprise-grade REST APIs
* Understand Spring Boot request lifecycle
* Master JPA entity relationships and Hibernate internals
* Apply layered architecture and clean code principles
* Work with transactions and persistence context
* Implement efficient database queries using JPA
* Build reusable and maintainable service layers
* Improve debugging, logging, and testing practices
* Develop backend applications following industry best practices

This project is the second step in my backend development roadmap, with the goal of becoming a proficient Spring Boot backend developer capable of designing scalable, maintainable, and production-ready applications.

## Folder Structure
```
src
└── main
    ├── controller
    ├── dto
    │    ├── request
    │    └── response
    ├── entity
    ├── enums        
    ├── repository
    ├── service
    │     └── impl
    ├── mapper
    ├── exception
    ├── advice
    ├── config
    ├── util
    ├── validation
    └── constants
```
