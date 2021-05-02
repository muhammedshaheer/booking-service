package com.github.muhammedshaheer.bookingservice.service;

import com.github.muhammedshaheer.bookingservice.dto.request.AddHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.AddReviewRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.AddRoomRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.HotelFilterRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateRoomRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelMinimalResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelReviewResponseDTO;
import com.github.muhammedshaheer.bookingservice.entity.Hotel;
import com.github.muhammedshaheer.bookingservice.entity.Review;
import com.github.muhammedshaheer.bookingservice.entity.Room;
import com.github.muhammedshaheer.bookingservice.entity.User;
import com.github.muhammedshaheer.bookingservice.entity.enums.BedType;
import com.github.muhammedshaheer.bookingservice.entity.enums.RoomType;
import com.github.muhammedshaheer.bookingservice.exception.BookingServiceException;
import com.github.muhammedshaheer.bookingservice.repository.HotelRepository;
import com.github.muhammedshaheer.bookingservice.repository.ReviewRepository;
import com.github.muhammedshaheer.bookingservice.repository.RoomRepository;
import com.github.muhammedshaheer.bookingservice.repository.UserRepository;
import com.github.muhammedshaheer.bookingservice.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    public void test_create_hotel_success() {
        AddHotelRequestDTO addHotelRequest = new AddHotelRequestDTO();
        addHotelRequest.setName("Hotel Name");
        addHotelRequest.setLocation("Hotel Location");
        addHotelRequest.setDefaultCheckInTime("12:00 PM");
        addHotelRequest.setDefaultCheckOutTime("11:00 AM");

        AddRoomRequestDTO addRoomRequest = new AddRoomRequestDTO();
        addRoomRequest.setRoomType("DOUBLE");
        addRoomRequest.setBedType("KING");
        addRoomRequest.setNumberOfAdults(2);
        addRoomRequest.setNumberOfChildren(1);
        addRoomRequest.setNumberOfRooms(4);
        addRoomRequest.setBasicFare(1000L);
        addRoomRequest.setTaxPercentage(18L);
        addHotelRequest.setRooms(Collections.singletonList(addRoomRequest));

        hotelService.createHotel(addHotelRequest);
    }

    @Test
    public void test_create_user_invalid_room_type() {
        AddHotelRequestDTO addHotelRequest = new AddHotelRequestDTO();
        addHotelRequest.setName("Hotel Name");
        addHotelRequest.setLocation("Hotel Location");
        addHotelRequest.setDefaultCheckInTime("12:00 PM");
        addHotelRequest.setDefaultCheckOutTime("11:00 AM");

        AddRoomRequestDTO addRoomRequest = new AddRoomRequestDTO();
        addRoomRequest.setRoomType("DOUBLES");
        addRoomRequest.setBedType("KING");
        addRoomRequest.setNumberOfAdults(2);
        addRoomRequest.setNumberOfChildren(1);
        addRoomRequest.setNumberOfRooms(4);
        addRoomRequest.setBasicFare(1000L);
        addRoomRequest.setTaxPercentage(18L);
        addHotelRequest.setRooms(Collections.singletonList(addRoomRequest));

        assertThrows(BookingServiceException.class, () -> hotelService.createHotel(addHotelRequest));
    }

    @Test
    public void test_create_user_invalid_bed_type() {
        AddHotelRequestDTO addHotelRequest = new AddHotelRequestDTO();
        addHotelRequest.setName("Hotel Name");
        addHotelRequest.setLocation("Hotel Location");
        addHotelRequest.setDefaultCheckInTime("12:00 PM");
        addHotelRequest.setDefaultCheckOutTime("11:00 AM");

        AddRoomRequestDTO addRoomRequest = new AddRoomRequestDTO();
        addRoomRequest.setRoomType("DOUBLE");
        addRoomRequest.setBedType("KINGS");
        addRoomRequest.setNumberOfAdults(2);
        addRoomRequest.setNumberOfChildren(1);
        addRoomRequest.setNumberOfRooms(4);
        addRoomRequest.setBasicFare(1000L);
        addRoomRequest.setTaxPercentage(18L);
        addHotelRequest.setRooms(Collections.singletonList(addRoomRequest));

        assertThrows(BookingServiceException.class, () -> hotelService.createHotel(addHotelRequest));
    }

    @Test
    public void test_update_user_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        UpdateHotelRequestDTO updateHotelRequest = new UpdateHotelRequestDTO();
        updateHotelRequest.setName("Hotel Name");
        updateHotelRequest.setLocation("Hotel Location");
        updateHotelRequest.setDefaultCheckInTime("12:00 PM");
        updateHotelRequest.setDefaultCheckOutTime("11:00 AM");

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.updateHotel(hotelId, updateHotelRequest));
    }

    @Test
    public void test_update_user_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        UpdateHotelRequestDTO updateHotelRequest = new UpdateHotelRequestDTO();
        updateHotelRequest.setName("Hotel Name");
        updateHotelRequest.setLocation("Hotel Location");
        updateHotelRequest.setDefaultCheckInTime("12:00 PM");
        updateHotelRequest.setDefaultCheckOutTime("11:00 AM");

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.updateHotel(hotelId, updateHotelRequest));
    }

    @Test
    public void test_update_hotel_success() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        UpdateHotelRequestDTO updateHotelRequest = new UpdateHotelRequestDTO();
        updateHotelRequest.setName("Hotel Name");
        updateHotelRequest.setLocation("Hotel Location");
        updateHotelRequest.setDefaultCheckInTime("12:00 PM");
        updateHotelRequest.setDefaultCheckOutTime("11:00 AM");

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyActiveHotelObject()));
        hotelService.updateHotel(hotelId, updateHotelRequest);
    }

    @Test
    public void test_update_room_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        UpdateRoomRequestDTO updateRoomRequest = new UpdateRoomRequestDTO();
        updateRoomRequest.setRoomType("DOUBLE");
        updateRoomRequest.setBedType("KING");
        updateRoomRequest.setNumberOfAdults(2);
        updateRoomRequest.setNumberOfChildren(1);
        updateRoomRequest.setNumberOfRooms(4);
        updateRoomRequest.setBasicFare(1000L);
        updateRoomRequest.setTaxPercentage(18L);

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.updateRoom(hotelId, roomId, updateRoomRequest));
    }

    @Test
    public void test_update_room_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        UpdateRoomRequestDTO updateRoomRequest = new UpdateRoomRequestDTO();
        updateRoomRequest.setRoomType("DOUBLE");
        updateRoomRequest.setBedType("KING");
        updateRoomRequest.setNumberOfAdults(2);
        updateRoomRequest.setNumberOfChildren(1);
        updateRoomRequest.setNumberOfRooms(4);
        updateRoomRequest.setBasicFare(1000L);
        updateRoomRequest.setTaxPercentage(18L);

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.updateRoom(hotelId, roomId, updateRoomRequest));
    }

    @Test
    public void test_update_room_invalid_room_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        UpdateRoomRequestDTO updateRoomRequest = new UpdateRoomRequestDTO();
        updateRoomRequest.setRoomType("DOUBLE");
        updateRoomRequest.setBedType("KING");
        updateRoomRequest.setNumberOfAdults(2);
        updateRoomRequest.setNumberOfChildren(1);
        updateRoomRequest.setNumberOfRooms(4);
        updateRoomRequest.setBasicFare(1000L);
        updateRoomRequest.setTaxPercentage(18L);

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(roomRepository.findByRoomIdAndHotel(roomId, optionalHotel.get())).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.updateRoom(hotelId, roomId, updateRoomRequest));
    }

    @Test
    public void test_update_room_inactive_room_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        UpdateRoomRequestDTO updateRoomRequest = new UpdateRoomRequestDTO();
        updateRoomRequest.setRoomType("DOUBLE");
        updateRoomRequest.setBedType("KING");
        updateRoomRequest.setNumberOfAdults(2);
        updateRoomRequest.setNumberOfChildren(1);
        updateRoomRequest.setNumberOfRooms(4);
        updateRoomRequest.setBasicFare(1000L);
        updateRoomRequest.setTaxPercentage(18L);

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(roomRepository.findByRoomIdAndHotel(roomId, optionalHotel.get())).thenReturn(Optional.of(dummyInactiveRoomObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.updateRoom(hotelId, roomId, updateRoomRequest));
    }

    @Test
    public void test_update_room_success() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        UpdateRoomRequestDTO updateRoomRequest = new UpdateRoomRequestDTO();
        updateRoomRequest.setRoomType("DOUBLE");
        updateRoomRequest.setBedType("KING");
        updateRoomRequest.setNumberOfAdults(2);
        updateRoomRequest.setNumberOfChildren(1);
        updateRoomRequest.setNumberOfRooms(4);
        updateRoomRequest.setBasicFare(1000L);
        updateRoomRequest.setTaxPercentage(18L);

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(roomRepository.findByRoomIdAndHotel(roomId, optionalHotel.get())).thenReturn(Optional.of(dummyActiveRoomObject()));
        hotelService.updateRoom(hotelId, roomId, updateRoomRequest);
    }

    @Test
    public void test_delete_hotel_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.deleteHotel(hotelId));
    }

    @Test
    public void test_delete_hotel_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.deleteHotel(hotelId));
    }

    @Test
    public void test_delete_room_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.deleteRoom(hotelId, roomId));
    }

    @Test
    public void test_delete_room_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.deleteRoom(hotelId, roomId));
    }

    @Test
    public void test_delete_room_invalid_room_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(roomRepository.findByRoomIdAndHotel(roomId, optionalHotel.get())).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.deleteRoom(hotelId, roomId));
    }

    @Test
    public void test_delete_room_inactive_room_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(roomRepository.findByRoomIdAndHotel(roomId, optionalHotel.get())).thenReturn(Optional.of(dummyInactiveRoomObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.deleteRoom(hotelId, roomId));
    }

    @Test
    public void test_delete_room_success() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String roomId = "52b293ac-146c-451e-8ae5-8c7d31a792d3";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(roomRepository.findByRoomIdAndHotel(roomId, optionalHotel.get())).thenReturn(Optional.of(dummyActiveRoomObject()));
        hotelService.deleteRoom(hotelId, roomId);
    }

    @Test
    public void test_get_hotel_details_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.getHotelDetails(hotelId));
    }

    @Test
    public void test_get_hotel_details_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.getHotelDetails(hotelId));
    }

    @Test
    public void test_get_hotel_details_success() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyActiveHotelObject()));
        hotelService.getHotelDetails(hotelId);
    }

    @Test
    public void test_add_review_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        AddReviewRequestDTO addReviewRequest = new AddReviewRequestDTO();
        addReviewRequest.setComment("Comment");
        addReviewRequest.setRatingValue(8.3);
        addReviewRequest.setUserId("64eae204-5675-4402-bd9e-0abefd712f4f");

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.addReview(hotelId, addReviewRequest));
    }

    @Test
    public void test_add_review_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        AddReviewRequestDTO addReviewRequest = new AddReviewRequestDTO();
        addReviewRequest.setComment("Comment");
        addReviewRequest.setRatingValue(8.3);
        addReviewRequest.setUserId("64eae204-5675-4402-bd9e-0abefd712f4f");

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.addReview(hotelId, addReviewRequest));
    }

    @Test
    public void test_add_review_success() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        AddReviewRequestDTO addReviewRequest = new AddReviewRequestDTO();
        addReviewRequest.setComment("Comment");
        addReviewRequest.setRatingValue(8.3);
        addReviewRequest.setUserId("64eae204-5675-4402-bd9e-0abefd712f4f");

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyActiveHotelObject()));
        hotelService.addReview(hotelId, addReviewRequest);
    }

    @Test
    public void test_get_hotel_reviews_invalid_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "MALE";
        String residentialCity = "Kannur";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> hotelService.getHotelReviews(hotelId, gender, residentialCity));
    }

    @Test
    public void test_get_hotel_reviews_inactive_hotel_id() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "MALE";
        String residentialCity = "Kannur";

        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(Optional.of(dummyInactiveHotelObject()));
        assertThrows(BookingServiceException.class, () -> hotelService.getHotelReviews(hotelId, gender, residentialCity));
    }

    @Test
    public void test_get_hotel_reviews_with_empty_review_returns_empty_list() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "MALE";
        String residentialCity = "Kannur";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(Collections.emptyList());

        Assertions.assertIterableEquals(Collections.emptyList(), hotelService.getHotelReviews(hotelId, gender, residentialCity).getReviews());
    }

    @Test
    public void test_get_hotel_reviews_with_inactive_review_returns_empty_list() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "MALE";
        String residentialCity = "Kannur";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(Collections.singletonList(dummyInactiveReviewObject()));

        Assertions.assertIterableEquals(Collections.emptyList(), hotelService.getHotelReviews(hotelId, gender, residentialCity).getReviews());
    }

    @Test
    public void test_get_hotel_reviews_with_incorrect_gender_returns_empty_list() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "FEMALE";
        String residentialCity = "Kannur";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(Collections.singletonList(dummyActiveReviewObject()));

        Assertions.assertIterableEquals(Collections.emptyList(), hotelService.getHotelReviews(hotelId, gender, residentialCity).getReviews());
    }

    @Test
    public void test_get_hotel_reviews_with_incorrect_city_returns_empty_list() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "MALE";
        String residentialCity = "Calicut";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(Collections.singletonList(dummyActiveReviewObject()));

        Assertions.assertIterableEquals(Collections.emptyList(), hotelService.getHotelReviews(hotelId, gender, residentialCity).getReviews());
    }

    @Test
    public void test_get_hotel_reviews_with_incorrect_city_and_gender_returns_empty_list() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";
        String gender = "FEMALE";
        String residentialCity = "Calicut";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(Collections.singletonList(dummyActiveReviewObject()));

        Assertions.assertIterableEquals(Collections.emptyList(), hotelService.getHotelReviews(hotelId, gender, residentialCity).getReviews());
    }

    @Test
    public void test_get_hotel_reviews_with_no_filters_return_all_active_reviews() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(Collections.singletonList(dummyActiveReviewObject()));

        HotelReviewResponseDTO hotelReviews = hotelService.getHotelReviews(hotelId, "", "");
        int actualLength = hotelReviews.getReviews().size();
        Assertions.assertEquals(1, actualLength);
    }

    @Test
    public void test_get_hotel_reviews_return_all_active_reviews_with_average_rating() {
        String hotelId = "372fe5fa-4026-4d5d-baa7-369b1aaba093";

        Optional<Hotel> optionalHotel = Optional.of(dummyActiveHotelObject());
        Mockito.when(hotelRepository.findByHotelId(hotelId)).thenReturn(optionalHotel);
        Review review1 = dummyActiveReviewObject();
        review1.setRatingValue(8.5);
        Review review2 = dummyActiveReviewObject();
        review2.setRatingValue(8.3);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        Mockito.when(reviewRepository.findByHotel(optionalHotel.get())).thenReturn(reviews);

        HotelReviewResponseDTO hotelReviews = hotelService.getHotelReviews(hotelId, "", "");
        Double actualAverageRating = hotelReviews.getAverageRating();
        Long actualRatingCount = hotelReviews.getRatingCount();
        Assertions.assertEquals(8.4, actualAverageRating);
        Assertions.assertEquals(2, actualRatingCount);
    }

    @Test
    public void test_get_hotels_by_filter_returns_empty_list_for_incorrect_location() {
        HotelFilterRequestDTO hotelFilterRequest = new HotelFilterRequestDTO();
        hotelFilterRequest.setCity("Calicut");

        Mockito.when(hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity())).thenReturn(Collections.emptyList());
        List<HotelMinimalResponseDTO> hotelsByFilter = hotelService.getHotelsByFilter(hotelFilterRequest);
        int actualLength = hotelsByFilter.size();
        Assertions.assertEquals(0, actualLength);
    }

    @Test
    public void test_get_hotels_by_filter_returns_empty_list_for_insufficient_rooms() {
        HotelFilterRequestDTO hotelFilterRequest = new HotelFilterRequestDTO();
        hotelFilterRequest.setCity("Calicut");
        hotelFilterRequest.setNumberOfRoomsRequired(5L);

        Mockito.when(hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity())).thenReturn(Collections.singletonList(dummyActiveHotelObject()));
        List<HotelMinimalResponseDTO> hotelsByFilter = hotelService.getHotelsByFilter(hotelFilterRequest);
        int actualLength = hotelsByFilter.size();
        Assertions.assertEquals(0, actualLength);
    }

    @Test
    public void test_get_hotels_by_filter_returns_empty_list_for_insufficient_guests() {
        HotelFilterRequestDTO hotelFilterRequest = new HotelFilterRequestDTO();
        hotelFilterRequest.setCity("Calicut");
        hotelFilterRequest.setNumberOfTravellers(5L);

        Mockito.when(hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity())).thenReturn(Collections.singletonList(dummyActiveHotelObject()));
        List<HotelMinimalResponseDTO> hotelsByFilter = hotelService.getHotelsByFilter(hotelFilterRequest);
        int actualLength = hotelsByFilter.size();
        Assertions.assertEquals(0, actualLength);
    }

    @Test
    public void test_get_hotels_by_filter_returns_empty_list_for_insufficient_rating() {
        HotelFilterRequestDTO hotelFilterRequest = new HotelFilterRequestDTO();
        hotelFilterRequest.setCity("Calicut");
        hotelFilterRequest.setRating(9.0);

        Mockito.when(hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity())).thenReturn(Collections.singletonList(dummyActiveHotelObject()));
        List<HotelMinimalResponseDTO> hotelsByFilter = hotelService.getHotelsByFilter(hotelFilterRequest);
        int actualLength = hotelsByFilter.size();
        Assertions.assertEquals(0, actualLength);
    }

    @Test
    public void test_get_hotels_by_filter_returns_empty_list_for_insufficient_facility() {
        HotelFilterRequestDTO hotelFilterRequest = new HotelFilterRequestDTO();
        hotelFilterRequest.setCity("Calicut");
        hotelFilterRequest.setFacilities(Collections.singletonList("Laundry"));

        Mockito.when(hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity())).thenReturn(Collections.singletonList(dummyActiveHotelObject()));
        List<HotelMinimalResponseDTO> hotelsByFilter = hotelService.getHotelsByFilter(hotelFilterRequest);
        int actualLength = hotelsByFilter.size();
        Assertions.assertEquals(0, actualLength);
    }

    @Test
    public void test_get_hotels_by_filter_returns_element_with_satisfying_all_filters() {
        HotelFilterRequestDTO hotelFilterRequest = new HotelFilterRequestDTO();
        hotelFilterRequest.setCity("Calicut");
        hotelFilterRequest.setNumberOfRoomsRequired(1L);
        hotelFilterRequest.setNumberOfTravellers(2L);
        hotelFilterRequest.setRating(8.0);
        hotelFilterRequest.setFacilities(Collections.singletonList("Air Conditioner"));

        Mockito.when(hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity())).thenReturn(Collections.singletonList(dummyActiveHotelObject()));
        List<HotelMinimalResponseDTO> hotelsByFilter = hotelService.getHotelsByFilter(hotelFilterRequest);
        int actualLength = hotelsByFilter.size();
        Assertions.assertEquals(1, actualLength);
    }

    private Hotel dummyInactiveHotelObject() {
        Hotel hotel = new Hotel();
        hotel.setHotelId("372fe5fa-4026-4d5d-baa7-369b1aaba093");
        hotel.setName("Hotel Name");
        hotel.setLocation("Hotel Location");
        hotel.setDefaultCheckInTime("12:00 PM");
        hotel.setDefaultCheckOutTime("11:00 AM");
        hotel.setDeleted(true);
        Room room = new Room();
        room.setRoomId("372fe5fa-4026-4d5d-baa7-369b1aaba092");
        room.setRoomType(RoomType.DOUBLE);
        room.setBedType(BedType.KING);
        room.setNumberOfAdults(2);
        room.setNumberOfChildren(1);
        room.setNumberOfRooms(4);
        room.setBasicFare(1000L);
        room.setTaxPercentage(18L);
        hotel.setRooms(Collections.singletonList(room));
        return hotel;
    }

    private Hotel dummyActiveHotelObject() {
        Hotel hotel = new Hotel();
        hotel.setHotelId("372fe5fa-4026-4d5d-baa7-369b1aaba093");
        hotel.setName("Hotel Name");
        hotel.setLocation("Hotel Location");
        hotel.setDefaultCheckInTime("12:00 PM");
        hotel.setDefaultCheckOutTime("11:00 AM");
        hotel.setFacilities("Wifi,Parking");
        hotel.setDeleted(false);
        Room room = new Room();
        room.setRoomId("372fe5fa-4026-4d5d-baa7-369b1aaba092");
        room.setRoomType(RoomType.DOUBLE);
        room.setBedType(BedType.KING);
        room.setNumberOfAdults(2);
        room.setNumberOfChildren(1);
        room.setNumberOfRooms(4);
        room.setBasicFare(1000L);
        room.setTaxPercentage(18L);
        room.setFacilities("Air Conditioner");
        room.setDeleted(false);
        hotel.setRooms(Collections.singletonList(room));
        Review review = new Review();
        review.setReviewId("372fe5fa-4026-4d5d-baa7-369b1aaba092");
        review.setComment("Comment");
        review.setRatingValue(8.3);
        review.setReviewedBy(dummyActiveUserObject());
        review.setReviewedDate(new Date());
        review.setDeleted(false);
        hotel.setReviews(Collections.singletonList(review));
        return hotel;
    }

    private Room dummyInactiveRoomObject() {
        Room room = new Room();
        room.setRoomId("372fe5fa-4026-4d5d-baa7-369b1aaba092");
        room.setRoomType(RoomType.DOUBLE);
        room.setBedType(BedType.KING);
        room.setNumberOfAdults(2);
        room.setNumberOfChildren(1);
        room.setNumberOfRooms(4);
        room.setBasicFare(1000L);
        room.setTaxPercentage(18L);
        room.setDeleted(true);
        return room;
    }

    private Room dummyActiveRoomObject() {
        Room room = new Room();
        room.setRoomId("372fe5fa-4026-4d5d-baa7-369b1aaba092");
        room.setRoomType(RoomType.DOUBLE);
        room.setBedType(BedType.KING);
        room.setNumberOfAdults(2);
        room.setNumberOfChildren(1);
        room.setNumberOfRooms(4);
        room.setBasicFare(1000L);
        room.setTaxPercentage(18L);
        room.setDeleted(false);
        return room;
    }

    public Review dummyInactiveReviewObject() {
        String reviewId = "372fe5fa-4026-4d5d-baa7-369b1aaba092";
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setComment("Comment");
        review.setRatingValue(8.3);
        review.setReviewedBy(dummyActiveUserObject());
        review.setReviewedDate(new Date());
        review.setHotel(dummyActiveHotelObject());
        review.setDeleted(true);
        return review;
    }

    public Review dummyActiveReviewObject() {
        String reviewId = "372fe5fa-4026-4d5d-baa7-369b1aaba092";
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setComment("Comment");
        review.setRatingValue(8.3);
        review.setReviewedBy(dummyActiveUserObject());
        review.setReviewedDate(new Date());
        review.setHotel(dummyActiveHotelObject());
        review.setDeleted(false);
        return review;
    }

    private User dummyActiveUserObject() {
        User user = new User();
        user.setUserId("64eae204-5675-4402-bd9e-0abefd712f4f");
        user.setEmail("ckshaheer2012@gmail.com");
        user.setFirstName("Muhammed");
        user.setLastName("Shaheer");
        user.setGender("MALE");
        user.setResidentialCity("Kannur");
        user.setContactNumber("+91-9847012345");
        user.setDeleted(false);
        return user;
    }
}
