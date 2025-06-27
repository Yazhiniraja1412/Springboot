package com.cozyhaven.dto;

import java.time.LocalDate;

public record BookingRequest(
        Long roomId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        int adults,
        int children
) {}
