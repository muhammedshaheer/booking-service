package com.github.muhammedshaheer.bookingservice.repository;

import com.github.muhammedshaheer.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to handle booking entity
 *
 * @author Muhammed Shaheer
 */

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
