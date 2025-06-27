package com.cozyhaven.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cozyhaven.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByHotelId(Long hotelId);
}
