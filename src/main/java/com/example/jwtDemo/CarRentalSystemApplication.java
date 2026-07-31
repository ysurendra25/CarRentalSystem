package com.example.jwtDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
	    "CarController",
	    "CarDto",
	    "CarEntity",
	    "CarRepo",
	    "CarService",
	    "CarException",
	    "CarHandler",
	    "CarConfig",
	    "Security1"
	})
@EnableJpaRepositories(basePackages = "CarRepo")
@EntityScan(basePackages = "CarEntity")
public class CarRentalSystemApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(CarRentalSystemApplication.class, args);
	}

}
