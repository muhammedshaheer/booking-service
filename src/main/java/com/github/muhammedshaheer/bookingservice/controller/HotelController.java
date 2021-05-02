package com.github.muhammedshaheer.bookingservice.controller;

import com.github.muhammedshaheer.bookingservice.dto.ResponseInfo;
import com.github.muhammedshaheer.bookingservice.dto.request.AddHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.AddReviewRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.HotelFilterRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateRoomRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelMinimalResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelReviewResponseDTO;
import com.github.muhammedshaheer.bookingservice.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Muhammed Shaheer
 */

@RequestMapping("/hotels")
@RestController
public class HotelController {
    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseInfo<?> createHotel(@Valid @RequestBody AddHotelRequestDTO addHotelRequest) {
        logger.info("Request received to create hotel information");
        HotelResponseDTO hotelResponse = hotelService.createHotel(addHotelRequest);
        return new ResponseInfo<>(hotelResponse);
    }

    @PutMapping("/{hotelId}")
    public ResponseInfo<?> updateHotel(@PathVariable("hotelId") String hotelId,
                                       @Valid @RequestBody UpdateHotelRequestDTO updateHotelRequest) {
        logger.info("Request received to update hotel information | hotelId:{}", hotelId);
        HotelResponseDTO hotelResponse = hotelService.updateHotel(hotelId, updateHotelRequest);
        return new ResponseInfo<>(hotelResponse);
    }

    @PutMapping("/{hotelId}/rooms/{roomId}")
    public ResponseInfo<?> updateRoom(@PathVariable("hotelId") String hotelId,
                                      @PathVariable("roomId") String roomId,
                                      @Valid @RequestBody UpdateRoomRequestDTO updateRoomRequest) {
        logger.info("Request received to update room information | hotelId:{},roomId:{}", hotelId, roomId);
        HotelResponseDTO hotelResponse = hotelService.updateRoom(hotelId, roomId, updateRoomRequest);
        return new ResponseInfo<>(hotelResponse);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseInfo<?> deleteHotel(@PathVariable("hotelId") String hotelId) {
        logger.info("Request received to delete hotel information | hotelId:{}", hotelId);
        hotelService.deleteHotel(hotelId);
        return new ResponseInfo<>();
    }

    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    public ResponseInfo<?> deleteRoom(@PathVariable("hotelId") String hotelId,
                                      @PathVariable("roomId") String roomId) {
        logger.info("Request received to delete room information | hotelId:{},roomId:{}", hotelId, roomId);
        HotelResponseDTO hotelResponse = hotelService.deleteRoom(hotelId, roomId);
        return new ResponseInfo<>(hotelResponse);
    }

    @GetMapping("/{hotelId}")
    public ResponseInfo<?> getHotelDetails(@PathVariable("hotelId") String hotelId) {
        logger.info("Request received to fetch hotel details | hotelId:{}", hotelId);
        HotelResponseDTO hotelResponse = hotelService.getHotelDetails(hotelId);
        return new ResponseInfo<>(hotelResponse);
    }

    @PostMapping("/{hotelId}/reviews")
    public ResponseInfo<?> createReview(@PathVariable("hotelId") String hotelId,
                                        @Valid @RequestBody AddReviewRequestDTO addReviewRequest) {
        logger.info("Request received to add review information for hotel | hotelId:{}", hotelId);
        HotelResponseDTO hotelResponseDTO = hotelService.addReview(hotelId, addReviewRequest);
        return new ResponseInfo<>(hotelResponseDTO);
    }

    @GetMapping("/{hotelId}/reviews")
    public ResponseInfo<?> getHotelReviews(@PathVariable("hotelId") String hotelId,
                                           @RequestParam(value = "gender", required = false) String gender,
                                           @RequestParam(value = "residentialCity", required = false) String residentialCity) {
        logger.info("Request received to fetch hotel reviews | hotelId:{}", hotelId);
        HotelReviewResponseDTO hotelReviewResponse = hotelService.getHotelReviews(hotelId, gender, residentialCity);
        return new ResponseInfo<>(hotelReviewResponse);
    }

    @PostMapping("/filter")
    public ResponseInfo<?> getHotelsByFilter(@Valid @RequestBody HotelFilterRequestDTO hotelFilterRequest) {
        logger.info("Request received to fetch hotels based on filters");
        List<HotelMinimalResponseDTO> hotelMinimalResponses = hotelService.getHotelsByFilter(hotelFilterRequest);
        return new ResponseInfo<>(hotelMinimalResponses);
    }
}
