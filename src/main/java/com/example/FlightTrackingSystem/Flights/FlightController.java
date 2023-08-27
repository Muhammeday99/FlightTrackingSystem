package com.example.FlightTrackingSystem.Flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1")
public class FlightController {
    private final FlightService flightService;
    private final AirportService airportService;
    @Autowired
    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @GetMapping(path = "/flights")
    public ResponseEntity<Object> getFlights(@RequestParam(required = false) String from,@RequestParam(required = false) String to,@RequestParam(required = false) String depDate,@RequestParam(required = false) String returnDate, @RequestParam(required = false) String type){
        LocalDateTime depDate_d = null;
        if(depDate != null) {
            try {
                depDate_d = LocalDateTime.parse(depDate).truncatedTo(ChronoUnit.SECONDS);
            } catch (DateTimeParseException e) {
                return new ResponseEntity<>("invalid date: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        LocalDateTime returnDate_d = null;
        if(returnDate != null) {
            try {
                returnDate_d = LocalDateTime.parse(returnDate).truncatedTo(ChronoUnit.SECONDS);
            } catch (DateTimeParseException e) {
                return new ResponseEntity<>("invalid date: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        Optional<Airport> depAirportOpt = null;
        Long depAirportId = null;
        if(from != null) {
            depAirportOpt = airportService.getAirportByCity(from.toLowerCase());
            if (depAirportOpt.isEmpty()) {
                return new ResponseEntity<>("departure airport not found", HttpStatus.NOT_FOUND);
            }
            depAirportId = depAirportOpt.get().getId();
        }
        Optional<Airport> arrAirportOpt = null;
        Long arrAirportId = null;
        if(to != null) {
            arrAirportOpt = airportService.getAirportByCity(to.toLowerCase());
            if (arrAirportOpt.isEmpty()) {
                return new ResponseEntity<>("arrival airport not found", HttpStatus.NOT_FOUND);
            }
            arrAirportId = arrAirportOpt.get().getId();
        }

        List<Flight> flights = flightService.getFlightsFiltered(depAirportId, arrAirportId, depDate_d, returnDate_d);
        if(type != null){
            if(type.equals("oneway")){
                List<Flight> temp = new LinkedList<>();
                for(Flight f : flights){
                    if(f.getReturnDate() == null){
                        temp.add(f);
                    }
                }
                flights = temp;
            } else if (type.equals("twoway")) {
                List<Flight> temp = new LinkedList<>();
                for(Flight f : flights){
                    if(f.getReturnDate() != null){
                        temp.add(f);
                    }
                }
                flights = temp;
            }
        }
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PostMapping(path = "/flights")
    public ResponseEntity<Object> createFlight(@RequestBody Flight flight){
        Optional<Airport> depAirport = airportService.getAirportById(flight.getDepartureAirport().getId());
        if(depAirport.isEmpty()){
            return new ResponseEntity<>("invalid departure airport id provided",HttpStatus.BAD_REQUEST);
        }
        Optional<Airport> arrAirport = airportService.getAirportById(flight.getArrivalAirport().getId());
        if(arrAirport.isEmpty()){
            return new ResponseEntity<>("invalid arrival airport id provided",HttpStatus.BAD_REQUEST);
        }
        flightService.createFlight(depAirport.get(),arrAirport.get(),flight.getDepartureDate(),flight.getReturnDate(),flight.getPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/flights/{id}")
    public ResponseEntity<Object> updateFlight(@RequestBody Flight flight, @PathVariable Long id){
        Optional<Flight> flightOpt = flightService.getFlight(id);
        if(flightOpt.isEmpty()){
            return new ResponseEntity<>("flight not found",HttpStatus.NOT_FOUND);
        }
        Optional<Airport> depAirport = airportService.getAirportById(flight.getDepartureAirport().getId());
        if(depAirport.isEmpty()){
            return new ResponseEntity<>("invalid departure airport id provided",HttpStatus.BAD_REQUEST);
        }
        Optional<Airport> arrAirport = airportService.getAirportById(flight.getArrivalAirport().getId());
        if(arrAirport.isEmpty()){
            return new ResponseEntity<>("invalid arrival airport id provided",HttpStatus.BAD_REQUEST);
        }
        flight.setId(id);
        flightService.updateFlight(flight);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/flights/{id}")
    public ResponseEntity<Object> deleteFlight(@PathVariable(name = "id") Long id){
        Optional<Flight> flight = flightService.getFlight(id);
        if(flight.isEmpty()){
            return new ResponseEntity<>("flight not found",HttpStatus.NOT_FOUND);
        }
        try {
            flightService.deleteFlight(id);
        }catch (Exception e){
            return new ResponseEntity<>("failed to delete flight err: " + e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 0 * * *")
    // this function mocks fetching flights from a third party API
    protected void fetchFlights(){
        List<Airport> airports = airportService.getAirports();
        Random rand = new Random();

        for(int i = 0;i<5;i++){
            int dep = rand.nextInt(airports.size());
            int arr = rand.nextInt(airports.size());
            while (dep == arr){
                arr = rand.nextInt(airports.size());
            }
            Flight newFlight = new Flight(airports.get(dep),airports.get(arr), LocalDateTime.of(2023,rand.nextInt(1,12),rand.nextInt(1,28),rand.nextInt(1,24),0),null,(float)rand.nextInt(100,1500));
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


    }
}
