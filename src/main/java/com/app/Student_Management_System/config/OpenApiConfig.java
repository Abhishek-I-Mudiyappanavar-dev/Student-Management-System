package com.app.Student_Management_System.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(apiInfo())
                .externalDocs(externalDocumentation());
    }

    private Info apiInfo(){
        return new Info()
                .title("Student Management System API")
                .description("""
                        REST API for managing students, instructors, departments
                        and academic information.
                        """)
                .version("v1.0.0")
                .contact(apiContact());
    }

    private Contact apiContact(){
        return new Contact()
                .name("Abhishek I Mudiyappanavar")
                .email("abhishek.i.mudiyappanavar@gmail.com")
                .url("https://github.com/Abhishek-I-Mudiyappanavar-dev");
    }

    private ExternalDocumentation externalDocumentation(){
        return new ExternalDocumentation()
                .description("Project Documentation")
                .url("https://github.com/Abhishek-I-Mudiyappanavar-dev/Student-Management-System.git");
    }
}
