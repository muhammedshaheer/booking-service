package com.github.muhammedshaheer.bookingservice.service;

import com.github.muhammedshaheer.bookingservice.dto.request.AddHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.AddReviewRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.HotelFilterRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateRoomRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelMinimalResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelReviewResponseDTO;

import java.util.List;

/**
 * Interface to handle hotel related services
 *
 * @author Muhammed Shaheer
 */

public interface HotelService {
    HotelResponseDTO createHotel(AddHotelRequestDTO addHotelRequest);

    HotelResponseDTO updateHotel(String hotelId, UpdateHotelRequestDTO updateHotelRequest);

    HotelResponseDTO updateRoom(String hotelId, String roomId, UpdateRoomRequestDTO updateRoomRequest);

    void deleteHotel(String hotelId);

    HotelResponseDTO deleteRoom(String hotelId, String roomId);

    HotelResponseDTO getHotelDetails(String hotelId);

    HotelResponseDTO addReview(String hotelId, AddReviewRequestDTO addReviewRequest);

    HotelReviewResponseDTO getHotelReviews(String hotelId, String gender, String residentialCity);

    List<HotelMinimalResponseDTO> getHotelsByFilter(HotelFilterRequestDTO hotelFilterRequest);
}
