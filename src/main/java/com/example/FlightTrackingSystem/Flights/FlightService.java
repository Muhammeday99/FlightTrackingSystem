package com.example.FlightTrackingSystem.Flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void createFlight(Airport departureAirport, Airport arrivalAirport, LocalDateTime departureDate, LocalDateTime returnDate, Float price){
        flightRepository.save(new Flight(departureAirport, arrivalAirport, departureDate, returnDate, price));
    }

    public void createFlight(Flight flight){
        flightRepository.save(flight);
    }

    public void updateFlight(Flight flight){
        flightRepository.save(flight);
    }

    public Optional<Flight> getFlight(Long id){
        return flightRepository.findById(id);
    }
    public List<Flight> getFlights(){
        return flightRepository.findAll();
    }

    public List<Flight> getFlightsFiltered(Long depId, Long arrId, LocalDateTime depDate, LocalDateTime returnDate){
        return flightRepository.getFlightsFiltered(depId, arrId, depDate, returnDate);
    }

    public void deleteFlight(Long id){
        flightRepository.deleteById(id);
    }
}
