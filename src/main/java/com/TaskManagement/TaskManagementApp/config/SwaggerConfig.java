package com.TaskManagement.TaskManagementApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management",
                description = "Simple project to start with spring boot, JPA and hibernate, also using a Postgres database in a RDS instance to get into AWS.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Daniel Guerra",
                        url = "https://www.linkedin.com/in/daniel-guerra-montoya/",
                        email = "danielguerracode@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "https://taskmanagment.com"
                ),
        }
)
public class SwaggerConfig {
}
