package com.cozyhaven.service;

import com.cozyhaven.entity.Hotel;
import com.cozyhaven.repository.HotelRepository;
import com.cozyhaven.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepo;

    public List<Hotel> findAll() {
        return hotelRepo.findAll();
    }

    public Hotel findById(Long id) {
        return hotelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
    }

    public Hotel save(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    public void delete(Long id) {
        hotelRepo.deleteById(id);
    }
}
