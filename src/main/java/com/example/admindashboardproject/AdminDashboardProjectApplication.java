package com.example.admindashboardproject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AdminDashboardProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminDashboardProjectApplication.class, args);
    }
}
