package com.cozyhaven.controller;

import com.cozyhaven.entity.Hotel;
import com.cozyhaven.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired private HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> list() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> get(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Hotel> add(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.save(hotel));
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
