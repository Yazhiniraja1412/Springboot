package com.cozyhaven.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cozyhaven.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
}
