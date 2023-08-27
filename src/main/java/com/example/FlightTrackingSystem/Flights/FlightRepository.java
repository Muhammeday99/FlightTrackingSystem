package com.example.FlightTrackingSystem.Flights;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE (:depId IS NULL OR f.departureAirport.id = :depId) AND (:arrId IS NULL OR f.arrivalAirport.id = :arrId) AND (cast(:depDate as date) IS NULL OR f.departureDate = :depDate) AND (cast(:returnDate as date) IS NULL OR f.returnDate = :returnDate)")
    List<Flight> getFlightsFiltered(@Param("depId") Long depId, @Param("arrId") Long arrId, @Param("depDate") LocalDateTime depDate, @Param("returnDate") LocalDateTime returnDate);
}
