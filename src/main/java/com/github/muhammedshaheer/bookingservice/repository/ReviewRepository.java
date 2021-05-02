package com.github.muhammedshaheer.bookingservice.repository;

import com.github.muhammedshaheer.bookingservice.entity.Hotel;
import com.github.muhammedshaheer.bookingservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to handle review entity
 *
 * @author Muhammed Shaheer
 */

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByHotel(Hotel hotel);
}
