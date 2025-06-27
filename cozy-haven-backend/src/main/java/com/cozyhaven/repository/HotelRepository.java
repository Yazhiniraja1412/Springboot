package com.cozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cozyhaven.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
