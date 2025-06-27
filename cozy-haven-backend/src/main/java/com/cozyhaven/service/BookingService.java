package com.cozyhaven.service;

import com.cozyhaven.entity.*;
import com.cozyhaven.repository.*;
import com.cozyhaven.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepo;
    @Autowired private RoomRepository roomRepo;
    @Autowired private UserRepository userRepo;

    public double calculateFare(Room room, int adults, int children, long nights) {
        double fare = room.getBaseFare() * nights;

        int extraAdults = Math.max(0, adults - room.getMaxOccupancyBase());
        int extraChildren = Math.max(0, children - (room.getMaxOccupancy() - room.getMaxOccupancyBase()));

        fare += extraAdults * room.getBaseFare() * 0.4 * nights;
        fare += extraChildren * room.getBaseFare() * 0.2 * nights;
        return fare;
    }

    @Transactional
    public Booking bookRoom(Long userId, Long roomId, java.time.LocalDate in, java.time.LocalDate out, int adults, int children) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if (!room.isAvailable()) throw new BookingException("Room already booked");
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        long nights = ChronoUnit.DAYS.between(in, out);
        double total = calculateFare(room, adults, children, nights);
        Booking booking = Booking.builder()
                .user(user)
                .room(room)
                .checkInDate(in)
                .checkOutDate(out)
                .adults(adults)
                .children(children)
                .totalFare(total)
                .status(Booking.Status.BOOKED)
                .build();
        room.setAvailable(false);
        roomRepo.save(room);
        return bookingRepo.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        if (booking.getStatus() != Booking.Status.BOOKED)
            throw new BookingException("Cannot cancel booking");
        booking.setStatus(Booking.Status.CANCELLED);
        booking.getRoom().setAvailable(true);
    }
}
