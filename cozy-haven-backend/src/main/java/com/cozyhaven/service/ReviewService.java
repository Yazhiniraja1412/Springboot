package com.cozyhaven.service;

import com.cozyhaven.entity.*;
import com.cozyhaven.repository.*;
import com.cozyhaven.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    @Autowired private ReviewRepository reviewRepo;
    @Autowired private HotelRepository hotelRepo;
    @Autowired private UserRepository userRepo;

    public Review addReview(Long userId, Long hotelId, int rating, String comment) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        Review review = Review.builder()
                .user(user)
                .hotel(hotel)
                .rating(rating)
                .comment(comment)
                .build();
        return reviewRepo.save(review);
    }

    public List<Review> listReviews(Long hotelId) {
        return reviewRepo.findByHotelId(hotelId);
    }
}
