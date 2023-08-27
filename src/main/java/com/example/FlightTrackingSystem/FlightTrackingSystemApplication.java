package com.example.FlightTrackingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightTrackingSystemApplication.class, args);
	}

}
