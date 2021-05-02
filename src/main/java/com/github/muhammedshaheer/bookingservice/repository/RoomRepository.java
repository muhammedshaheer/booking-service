package com.github.muhammedshaheer.bookingservice.repository;

import com.github.muhammedshaheer.bookingservice.entity.Hotel;
import com.github.muhammedshaheer.bookingservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Muhammed Shaheer
 */

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomIdAndHotel(String roomId, Hotel hotel);
}
