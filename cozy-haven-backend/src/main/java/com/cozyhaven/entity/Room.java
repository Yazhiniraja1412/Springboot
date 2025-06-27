package com.cozyhaven.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String roomSize; // e.g., "70 m²"
    private String bedType;  // SINGLE, DOUBLE, KING
    private int maxOccupancyBase; // base occupancy (2, 4)
    private int maxOccupancy;     // maximum occupancy (4,6)
    private double baseFare;
    private boolean ac;
    private boolean available = true;
}
