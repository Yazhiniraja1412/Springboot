package com.cozyhaven.controller;

import com.cozyhaven.dto.BookingRequest;
import com.cozyhaven.entity.Booking;
import com.cozyhaven.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> book(@RequestBody BookingRequest req, Authentication auth) {
        Booking booking = bookingService.bookRoom(
                ((com.cozyhaven.entity.User) auth.getPrincipal()).getId(),
                req.roomId(),
                req.checkInDate(),
                req.checkOutDate(),
                req.adults(),
                req.children()
        );
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> myBookings(Authentication auth) {
        Long userId = ((com.cozyhaven.entity.User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
