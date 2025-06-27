package com.cozyhaven.controller;

import com.cozyhaven.entity.Review;
import com.cozyhaven.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired private ReviewService reviewService;

    @PostMapping("/{hotelId}")
    public ResponseEntity<Review> add(@PathVariable Long hotelId,
                                      @RequestParam int rating,
                                      @RequestParam String comment,
                                      Authentication auth) {
        Long userId = ((com.cozyhaven.entity.User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(reviewService.addReview(userId, hotelId, rating, comment));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<List<Review>> list(@PathVariable Long hotelId) {
        return ResponseEntity.ok(reviewService.listReviews(hotelId));
    }
}
