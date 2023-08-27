package com.example.FlightTrackingSystem.Flights;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Configuration
public class FlightsConfig {

    @Bean
    CommandLineRunner flightCommandLineRunner(AirportService airportService, FlightService flightService) {
        return args -> {
            List<Airport> airports = airportService.getAirports();
            Random rand = new Random();

            for(int i = 0;i<5;i++){
                int dep = rand.nextInt(airports.size());
                int arr = rand.nextInt(airports.size());
                while (dep == arr){
                    arr = rand.nextInt(airports.size());
                }
                Flight newFlight = new Flight(airports.get(dep),airports.get(arr), LocalDateTime.of(2023,rand.nextInt(1,12),rand.nextInt(1,31),rand.nextInt(1,24),0),null,(float)rand.nextInt(100,1500));
                flightService.createFlight(newFlight);
            }

            for(int i = 0;i<5;i++){
                int dep = rand.nextInt(airports.size());
                int arr = rand.nextInt(airports.size());
                while (dep == arr){
                    arr = rand.nextInt(airports.size());
                }
                Flight newFlight = new Flight(airports.get(dep),airports.get(arr), LocalDateTime.of(2023,rand.nextInt(1,6),rand.nextInt(1,31),rand.nextInt(1,24),0), LocalDateTime.of(2023,rand.nextInt(6,12),rand.nextInt(1,31),rand.nextInt(1,24),0),(float)rand.nextInt(100,1500));
                flightService.createFlight(newFlight);
            }


        };
    }
}
