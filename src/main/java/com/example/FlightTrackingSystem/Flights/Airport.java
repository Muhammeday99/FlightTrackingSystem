package com.example.FlightTrackingSystem.Flights;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Airport {
    public Airport(Long id) {
        this.id = id;
    }

    public Airport(String city) {
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String city;
}
