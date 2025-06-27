package com.cozyhaven.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int adults;
    private int children;

    private double totalFare;

    @Enumerated(EnumType.STRING)
    private Status status = Status.BOOKED;

    public enum Status { BOOKED, CANCELLED, COMPLETED }
}
