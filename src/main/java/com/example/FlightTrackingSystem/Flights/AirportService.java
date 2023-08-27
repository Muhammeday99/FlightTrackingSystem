package com.example.FlightTrackingSystem.Flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    public final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAirports(){
        return airportRepository.findAll();
    }

    public Optional<Airport> getAirportById(Long id){
        return airportRepository.findById(id);
    }

    public Optional<Airport> getAirportByCity(String city){ return airportRepository.findAirportByCity(city);}

    public void createAirport(Airport airport){
        airportRepository.save(airport);
    }

    public void deleteAirport(Long id){
        airportRepository.deleteById(id);
    }

    public void updateAirport(Airport airport){
        airportRepository.save(airport);
    }
}
