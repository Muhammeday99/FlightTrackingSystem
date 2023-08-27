package com.example.FlightTrackingSystem.Flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class AirportController {
    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping(path = "/airports")
    public ResponseEntity<Object> createAirport(@RequestBody Airport airport){
        if(airport.getCity().isBlank()){
            return new ResponseEntity<>("invalid airport city name", HttpStatus.BAD_REQUEST);
        }
        airportService.createAirport(airport);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/airports")
    public ResponseEntity<Object> getAirports(){
        return new ResponseEntity<>(airportService.getAirports(), HttpStatus.OK);
    }

    @PutMapping(path = "/airports/{id}")
    public ResponseEntity<Object> updateAirport(@PathVariable(name = "id") Long id, @RequestBody Airport airport){
        Optional<Airport> airportOpt = airportService.getAirportById(id);
        if(airportOpt.isEmpty()){
            return new ResponseEntity<>("airport not found",HttpStatus.NOT_FOUND);
        }
        if(airport.getCity().isBlank()){
            return new ResponseEntity<>("invalid airport city name", HttpStatus.BAD_REQUEST);
        }
        airport.setId(id);
        airportService.updateAirport(airport);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/airports/{id}")
    public ResponseEntity<Object> deleteAirPort(@PathVariable(name = "id") Long id){
        Optional<Airport> airport = airportService.getAirportById(id);
        if(airport.isEmpty()){
            return new ResponseEntity<>("airport not found",HttpStatus.NOT_FOUND);
        }
        try {
            airportService.deleteAirport(id);
        }catch (Exception e){
            return new ResponseEntity<>("failed to delete airport err: " + e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
