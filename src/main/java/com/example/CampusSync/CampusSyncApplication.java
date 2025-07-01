package com.example.CampusSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.CampusSync")
//@EntityScan(basePackages = "com.example.CampusSync")
//@ComponentScan(basePackages = "com.example.CampusSync")
public class CampusSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusSyncApplication.class, args);
	}

}
