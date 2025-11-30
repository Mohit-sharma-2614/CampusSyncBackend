package com.example.CampusSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.CampusSync")
//@EntityScan(basePackages = "com.example.CampusSync")
//@ComponentScan(basePackages = "com.example.CampusSync")
public class CampusSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusSyncApplication.class, args);
	}

}
