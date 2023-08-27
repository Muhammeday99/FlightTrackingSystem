package com.example.FlightTrackingSystem.Flights;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AirportsConfig {
    @Bean
    CommandLineRunner commandLineRunner(AirportRepository airportRepository) {
        return args -> {
            airportRepository.saveAll(List.of(
                    new Airport("ankara"),
                    new Airport("istanbul"),
                    new Airport("izmir"),
                    new Airport("berlin"),
                    new Airport("london")
            ));
        };
    }
}
