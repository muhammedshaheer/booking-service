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
 * Controller class to handle hotel related APIs
 *
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

    /**
     * Endpoint to handle create hotel information
     *
     * @param addHotelRequest DTO with necessary hotel information to be created
     * @return success value as true/false
     */
    @PostMapping
    public ResponseInfo<?> createHotel(@Valid @RequestBody AddHotelRequestDTO addHotelRequest) {
        logger.info("Request received to create hotel information");
        HotelResponseDTO hotelResponse = hotelService.createHotel(addHotelRequest);
        return new ResponseInfo<>(hotelResponse);
    }

    /**
     * Endpoint to handle update hotel information
     *
     * @param hotelId            HotelId to which hotel information to be updated
     * @param updateHotelRequest DTO with necessary hotel information that are to be updated
     * @return success value as true/false
     */
    @PutMapping("/{hotelId}")
    public ResponseInfo<?> updateHotel(@PathVariable("hotelId") String hotelId,
                                       @Valid @RequestBody UpdateHotelRequestDTO updateHotelRequest) {
        logger.info("Request received to update hotel information | hotelId:{}", hotelId);
        HotelResponseDTO hotelResponse = hotelService.updateHotel(hotelId, updateHotelRequest);
        return new ResponseInfo<>(hotelResponse);
    }

    /**
     * Endpoint to update hotel room information
     *
     * @param hotelId           HotelId to which room information to be updated
     * @param roomId            RoomId to which room information to be updated
     * @param updateRoomRequest DTO with necessary room information that are to be updated
     * @return success value as true/false
     */
    @PutMapping("/{hotelId}/rooms/{roomId}")
    public ResponseInfo<?> updateRoom(@PathVariable("hotelId") String hotelId,
                                      @PathVariable("roomId") String roomId,
                                      @Valid @RequestBody UpdateRoomRequestDTO updateRoomRequest) {
        logger.info("Request received to update room information | hotelId:{},roomId:{}", hotelId, roomId);
        HotelResponseDTO hotelResponse = hotelService.updateRoom(hotelId, roomId, updateRoomRequest);
        return new ResponseInfo<>(hotelResponse);
    }

    /**
     * Endpoint to delete hotel information from system
     *
     * @param hotelId HotelId to which information are to be removed
     * @return success value as true/false
     */
    @DeleteMapping("/{hotelId}")
    public ResponseInfo<?> deleteHotel(@PathVariable("hotelId") String hotelId) {
        logger.info("Request received to delete hotel information | hotelId:{}", hotelId);
        hotelService.deleteHotel(hotelId);
        return new ResponseInfo<>();
    }

    /**
     * Endpoint to delete room information of a hotel from system
     *
     * @param hotelId HotelId to which room information to be removed
     * @param roomId  RoomId to which room information to be removed
     * @return success value as true/false
     */
    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    public ResponseInfo<?> deleteRoom(@PathVariable("hotelId") String hotelId,
                                      @PathVariable("roomId") String roomId) {
        logger.info("Request received to delete room information | hotelId:{},roomId:{}", hotelId, roomId);
        HotelResponseDTO hotelResponse = hotelService.deleteRoom(hotelId, roomId);
        return new ResponseInfo<>(hotelResponse);
    }

    /**
     * Endpoint to provide details of a hotel
     *
     * @param hotelId HotelId which hotel information to be fetched
     * @return response object with hotel information
     */
    @GetMapping("/{hotelId}")
    public ResponseInfo<?> getHotelDetails(@PathVariable("hotelId") String hotelId) {
        logger.info("Request received to fetch hotel details | hotelId:{}", hotelId);
        HotelResponseDTO hotelResponse = hotelService.getHotelDetails(hotelId);
        return new ResponseInfo<>(hotelResponse);
    }

    /**
     * Endpoint to add review for a hotel
     *
     * @param hotelId          HotelId which review information to be added
     * @param addReviewRequest DTO with necessary review information
     * @return response object with hotel information
     */
    @PostMapping("/{hotelId}/reviews")
    public ResponseInfo<?> createReview(@PathVariable("hotelId") String hotelId,
                                        @Valid @RequestBody AddReviewRequestDTO addReviewRequest) {
        logger.info("Request received to add review information for hotel | hotelId:{}", hotelId);
        HotelResponseDTO hotelResponseDTO = hotelService.addReview(hotelId, addReviewRequest);
        return new ResponseInfo<>(hotelResponseDTO);
    }

    /**
     * Endpoint to fetch hotel reviews
     *
     * @param hotelId         HotelId which reviews information to be fetched
     * @param gender          provide gender as filter which is optional
     * @param residentialCity provide city as filter which is optional
     * @return response object with hotel review and rating information
     */
    @GetMapping("/{hotelId}/reviews")
    public ResponseInfo<?> getHotelReviews(@PathVariable("hotelId") String hotelId,
                                           @RequestParam(value = "gender", required = false) String gender,
                                           @RequestParam(value = "residentialCity", required = false) String residentialCity) {
        logger.info("Request received to fetch hotel reviews | hotelId:{}", hotelId);
        HotelReviewResponseDTO hotelReviewResponse = hotelService.getHotelReviews(hotelId, gender, residentialCity);
        return new ResponseInfo<>(hotelReviewResponse);
    }

    /**
     * Endpoint to filter hotels with necessary filters that are eligible for booking
     *
     * @param hotelFilterRequest DTO with necessary filter parameters
     * @return response with minimal hotel information
     */
    @PostMapping("/filter")
    public ResponseInfo<?> getHotelsByFilter(@Valid @RequestBody HotelFilterRequestDTO hotelFilterRequest) {
        logger.info("Request received to fetch hotels based on filters");
        List<HotelMinimalResponseDTO> hotelMinimalResponses = hotelService.getHotelsByFilter(hotelFilterRequest);
        return new ResponseInfo<>(hotelMinimalResponses);
    }
}
