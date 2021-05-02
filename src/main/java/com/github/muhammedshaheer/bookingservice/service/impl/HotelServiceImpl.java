package com.github.muhammedshaheer.bookingservice.service.impl;

import com.github.muhammedshaheer.bookingservice.dto.request.AddHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.AddReviewRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.AddRoomRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.HotelFilterRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateHotelRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateRoomRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelMinimalResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.HotelReviewResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.ReviewResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.RoomMinimalResponseDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.RoomResponseDTO;
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
import com.github.muhammedshaheer.bookingservice.service.HotelService;
import com.github.muhammedshaheer.bookingservice.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Muhammed Shaheer
 */

@Service
public class HotelServiceImpl implements HotelService {
    private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public HotelServiceImpl(HotelRepository hotelRepository,
                            RoomRepository roomRepository,
                            ReviewRepository reviewRepository,
                            UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public HotelResponseDTO createHotel(AddHotelRequestDTO addHotelRequest) {
        Hotel hotel = new Hotel();
        String generatedHotelId = CommonUtil.getGeneratedId();
        hotel.setHotelId(generatedHotelId);
        hotel.setName(addHotelRequest.getName());
        hotel.setDescription(addHotelRequest.getDescription());
        hotel.setLocation(addHotelRequest.getLocation());
        hotel.setDefaultCheckInTime(addHotelRequest.getDefaultCheckInTime());
        hotel.setDefaultCheckOutTime(addHotelRequest.getDefaultCheckOutTime());
        if (addHotelRequest.getFacilities() != null) {
            hotel.setFacilities(String.join(",", addHotelRequest.getFacilities()));
        }
        hotel.setDeleted(false);
        Date createdDate = new Date();
        hotel.setCreatedDate(createdDate);
        hotel.setLastModifiedDate(createdDate);
        hotelRepository.save(hotel);

        List<AddRoomRequestDTO> roomRequests = addHotelRequest.getRooms();
        if (roomRequests != null && roomRequests.size() > 0) {
            List<Room> rooms = roomRequests.stream().map(roomRequest -> {
                Room room = new Room();
                String generatedRoomId = CommonUtil.getGeneratedId();
                room.setRoomId(generatedRoomId);
                RoomType roomType = RoomType.fromCode(roomRequest.getRoomType());
                room.setRoomType(roomType);
                BedType bedType = BedType.fromCode(roomRequest.getBedType());
                room.setBedType(bedType);
                room.setNumberOfAdults(roomRequest.getNumberOfAdults());
                room.setNumberOfChildren(roomRequest.getNumberOfChildren());
                room.setBasicFare(roomRequest.getBasicFare());
                room.setTaxPercentage(roomRequest.getTaxPercentage());
                if (roomRequest.getFacilities() != null) {
                    room.setFacilities(String.join(",", roomRequest.getFacilities()));
                }
                room.setCreatedDate(createdDate);
                room.setLastModifiedDate(createdDate);
                room.setDeleted(false);
                room.setHotel(hotel);
                return room;
            }).collect(Collectors.toList());
            roomRepository.saveAll(rooms);
            hotel.setRooms(rooms);
        }
        logger.info("Created hotel information successfully | hotelId:{}", generatedHotelId);
        return makeHotelResponseDTO(hotel);
    }

    @Override
    @Transactional
    public HotelResponseDTO updateHotel(String hotelId, UpdateHotelRequestDTO updateHotelRequest) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }
        hotel.setName(updateHotelRequest.getName());
        if (!ObjectUtils.isEmpty(updateHotelRequest.getDescription())) {
            hotel.setDescription(updateHotelRequest.getDescription());
        }
        hotel.setLocation(updateHotelRequest.getLocation());
        hotel.setDefaultCheckInTime(updateHotelRequest.getDefaultCheckInTime());
        hotel.setDefaultCheckOutTime(updateHotelRequest.getDefaultCheckOutTime());
        if (updateHotelRequest.getFacilities() != null && updateHotelRequest.getFacilities().size() > 0) {
            hotel.setFacilities(String.join(",", updateHotelRequest.getFacilities()));
        }
        Date lastModifiedDate = new Date();
        hotel.setLastModifiedDate(lastModifiedDate);
        hotelRepository.save(hotel);
        logger.info("Updated hotel information successfully | hotelId:{}", hotelId);
        return makeHotelResponseDTO(hotel);
    }

    @Override
    @Transactional
    public HotelResponseDTO updateRoom(String hotelId, String roomId, UpdateRoomRequestDTO updateRoomRequest) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }

        Optional<Room> optionalRoom = roomRepository.findByRoomIdAndHotel(roomId, hotel);
        Room room = optionalRoom.orElseThrow(() -> {
            logger.error("Hotel room information not found | hotelId:{},roomId:{}", hotelId, roomId);
            return new BookingServiceException("Hotel room information not found | hotelId:" + hotelId + ",roomId:" + roomId);
        });
        if (room.isDeleted()) {
            logger.info("Hotel room is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel room is inactive | hotelId:" + hotelId);
        }
        RoomType roomType = RoomType.fromCode(updateRoomRequest.getRoomType());
        room.setRoomType(roomType);
        BedType bedType = BedType.fromCode(updateRoomRequest.getBedType());
        room.setBedType(bedType);
        room.setNumberOfAdults(updateRoomRequest.getNumberOfAdults());
        room.setNumberOfChildren(updateRoomRequest.getNumberOfChildren());
        room.setBasicFare(updateRoomRequest.getBasicFare());
        room.setTaxPercentage(updateRoomRequest.getTaxPercentage());
        if (updateRoomRequest.getFacilities() != null && updateRoomRequest.getFacilities().size() > 0) {
            room.setFacilities(String.join(",", updateRoomRequest.getFacilities()));
        }
        Date lastModifiedDate = new Date();
        room.setLastModifiedDate(lastModifiedDate);
        roomRepository.save(room);
        logger.info("Updated hotel room information successfully | hotelId:{},roomId:{}", hotelId, roomId);
        return makeHotelResponseDTO(hotel);
    }

    @Override
    public void deleteHotel(String hotelId) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }
        hotel.setDeleted(true);
        Date lastModifiedDate = new Date();
        hotel.setLastModifiedDate(lastModifiedDate);
        hotelRepository.save(hotel);

        List<Room> rooms = hotel.getRooms();
        if (rooms != null && rooms.size() > 0) {
            List<Room> roomList = rooms.stream()
                    .filter(room -> !room.isDeleted())
                    .peek(room -> {
                        room.setDeleted(true);
                        room.setLastModifiedDate(lastModifiedDate);
                    })
                    .collect(Collectors.toList());
            roomRepository.saveAll(roomList);
        }
        logger.info("Deleted hotel information successfully | hotelId:{}", hotelId);
    }

    @Override
    public HotelResponseDTO deleteRoom(String hotelId, String roomId) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }
        Optional<Room> optionalRoom = roomRepository.findByRoomIdAndHotel(roomId, hotel);
        Room room = optionalRoom.orElseThrow(() -> {
            logger.error("Hotel room information not found | hotelId:{},roomId:{}", hotelId, roomId);
            return new BookingServiceException("Hotel room information not found | hotelId:" + hotelId + ",roomId:" + roomId);
        });
        if (room.isDeleted()) {
            logger.info("Hotel room is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel room is inactive | hotelId:" + hotelId);
        }
        room.setDeleted(true);
        Date lastModifiedDate = new Date();
        room.setLastModifiedDate(lastModifiedDate);
        roomRepository.save(room);
        logger.info("Deleted hotel room information successfully | hotelId:{},roomId:{}", hotelId, roomId);
        return makeHotelResponseDTO(hotel);
    }

    @Override
    public HotelResponseDTO getHotelDetails(String hotelId) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }
        logger.info("Fetched hotel information successfully | hotelId:{}", hotelId);
        return makeHotelResponseDTO(hotel);
    }

    @Override
    @Transactional
    public HotelResponseDTO addReview(String hotelId, AddReviewRequestDTO addReviewRequest) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }
        Review review = new Review();
        String generatedReviewId = CommonUtil.getGeneratedId();
        review.setReviewId(generatedReviewId);
        review.setHotel(hotel);
        review.setComment(addReviewRequest.getComment());
        review.setRatingValue(addReviewRequest.getRatingValue());
        Optional<User> optionalUser = userRepository.findByUserId(addReviewRequest.getUserId());
        optionalUser.ifPresent(review::setReviewedBy);
        Date reviewedDate = new Date();
        review.setReviewedDate(reviewedDate);
        review.setDeleted(false);
        reviewRepository.save(review);
        logger.info("Created hotel review information successfully | hotelId:{}", hotelId);
        return makeHotelResponseDTO(hotel);
    }

    @Override
    public HotelReviewResponseDTO getHotelReviews(String hotelId, String gender, String residentialCity) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelId(hotelId);
        Hotel hotel = optionalHotel.orElseThrow(() -> {
            logger.error("Hotel information not found | hotelId:{}", hotelId);
            return new BookingServiceException("Hotel information not found | hotelId:" + hotelId);
        });
        if (hotel.isDeleted()) {
            logger.info("Hotel is inactive | hotelId:{}", hotelId);
            throw new BookingServiceException("Hotel is inactive | hotelId:" + hotelId);
        }
        boolean filterByGender = !ObjectUtils.isEmpty(gender);
        boolean filterByCity = !ObjectUtils.isEmpty(residentialCity);
        List<Review> reviews = reviewRepository.findByHotel(hotel);
        logger.info("Fetched hotel reviews information successfully | hotelId:{}", hotelId);
        List<ReviewResponseDTO> reviewResponses = reviews.stream()
                .filter(review -> {
                    boolean isDeleted = !review.isDeleted();
                    boolean genderFilter = !filterByGender || (review.getReviewedBy() != null && review.getReviewedBy().getGender().equalsIgnoreCase(gender));
                    boolean cityFilter = !filterByCity || (review.getReviewedBy() != null && review.getReviewedBy().getResidentialCity().equalsIgnoreCase(residentialCity));
                    return isDeleted && genderFilter && cityFilter;
                })
                .map(review -> {
                    ReviewResponseDTO reviewResponse = new ReviewResponseDTO();
                    reviewResponse.setReviewId(review.getReviewId());
                    reviewResponse.setComment(review.getComment());
                    reviewResponse.setRatingValue(review.getRatingValue());
                    if (review.getReviewedBy() != null) {
                        reviewResponse.setReviewedBy(review.getReviewedBy().getFullName());
                    }
                    reviewResponse.setReviewedDate(review.getReviewedDate());
                    return reviewResponse;
                })
                .collect(Collectors.toList());
        long ratingCount = reviews.stream().filter(review -> !review.isDeleted()).count();
        double averageRating = reviews.stream()
                .filter(review -> !review.isDeleted())
                .collect(Collectors.summarizingDouble(Review::getRatingValue))
                .getAverage();

        HotelReviewResponseDTO hotelReviewResponse = new HotelReviewResponseDTO();
        hotelReviewResponse.setAverageRating(averageRating);
        hotelReviewResponse.setRatingCount(ratingCount);
        hotelReviewResponse.setReviews(reviewResponses);
        return hotelReviewResponse;
    }

    @Override
    public List<HotelMinimalResponseDTO> getHotelsByFilter(HotelFilterRequestDTO hotelFilterRequest) {
        List<Hotel> hotels = hotelRepository.findByLocationIgnoreCaseAndDeletedFalse(hotelFilterRequest.getCity());
        boolean filterByRooms = !ObjectUtils.isEmpty(hotelFilterRequest.getNumberOfRoomsRequired());
        boolean filterByTravellers = !ObjectUtils.isEmpty(hotelFilterRequest.getNumberOfTravellers());
        boolean filterByRating = !ObjectUtils.isEmpty(hotelFilterRequest.getRating());
        boolean filterByFacilities = hotelFilterRequest.getFacilities() != null && hotelFilterRequest.getFacilities().size() > 0;
        List<HotelMinimalResponseDTO> hotelMinimalResponses = hotels.stream()
                .filter(hotel -> {
                    boolean roomFilter = !filterByRooms || (
                            hotel.getRooms() != null && hotel.getRooms().size() > 0 &&
                                    getTotalNumberOfRooms(hotel) >= hotelFilterRequest.getNumberOfRoomsRequired()
                    );
                    boolean travelFilter = !filterByTravellers || (
                            hotel.getRooms() != null && hotel.getRooms().size() > 0 &&
                                    getTotalRoomsCapacity(hotel) >= hotelFilterRequest.getNumberOfTravellers()
                    );
                    boolean ratingFilter = !filterByRating || (
                            hotel.getReviews() != null && hotel.getReviews().size() > 0 &&
                                    getAverageRating(hotel) >= hotelFilterRequest.getRating()
                    );
                    List<String> facilities = new ArrayList<>();
                    if (filterByFacilities) {
                        facilities = getCombinedFacilities(hotel);
                    }
                    List<String> distinctHotelFacilities = facilities.stream().distinct().collect(Collectors.toList());
                    boolean facilitiesFilter = !filterByFacilities || (distinctHotelFacilities.containsAll(hotelFilterRequest.getFacilities()));
                    return roomFilter && travelFilter && ratingFilter && facilitiesFilter;
                })
                .map(this::makeHotelMinimalResponseDTO)
                .collect(Collectors.toList());
        logger.info("Fetched hotels information by filters successfully");
        String sortOrder = hotelFilterRequest.getSortOrder() != null ? hotelFilterRequest.getSortOrder().equalsIgnoreCase("DESC") ? "DESC" : "ASC" : "ASC";
        if ("COST".equalsIgnoreCase(hotelFilterRequest.getSortBy())) {
            hotelMinimalResponses.sort((o1, o2) -> {
                if (o1.getAverageRating().equals(o2.getAverageRating())) {
                    return 0;
                } else if (o1.getAverageRating() > o2.getAverageRating()) {
                    return sortOrder.equals("DESC") ? -1 : 1;
                } else {
                    return sortOrder.equals("DESC") ? 1 : -1;
                }
            });
        }
        return hotelMinimalResponses;
    }

    private HotelMinimalResponseDTO makeHotelMinimalResponseDTO(Hotel hotel) {
        HotelMinimalResponseDTO hotelMinimalResponse = new HotelMinimalResponseDTO();
        hotelMinimalResponse.setHotelId(hotel.getHotelId());
        hotelMinimalResponse.setName(hotel.getName());
        hotelMinimalResponse.setLocation(hotel.getLocation());
        if (hotel.getFacilities() != null) {
            hotelMinimalResponse.setFacilities(Arrays.asList(hotel.getFacilities().split(",")));
        }
        if (hotel.getRooms() != null && hotel.getRooms().size() > 0) {
            List<RoomMinimalResponseDTO> roomMinimalResponses = hotel.getRooms().stream()
                    .filter(room -> !room.isDeleted())
                    .map(room -> {
                        RoomMinimalResponseDTO roomMinimalResponse = new RoomMinimalResponseDTO();
                        roomMinimalResponse.setRoomType(room.getRoomType().getCode());
                        roomMinimalResponse.setBasicFare(room.getBasicFare());
                        roomMinimalResponse.setTaxPercentage(room.getTaxPercentage());
                        roomMinimalResponse.setNumberOfRooms(room.getNumberOfRooms());
                        roomMinimalResponse.setNumberOfAdults(room.getNumberOfAdults());
                        if (room.getFacilities() != null) {
                            roomMinimalResponse.setFacilities(Arrays.asList(room.getFacilities().split(",")));
                        }
                        return roomMinimalResponse;
                    })
                    .collect(Collectors.toList());
            hotelMinimalResponse.setRooms(roomMinimalResponses);
        }
        if (hotel.getReviews() != null && hotel.getReviews().size() > 0) {
            long ratingCount = hotel.getReviews().stream().filter(review -> !review.isDeleted()).count();
            double averageRating = getAverageRating(hotel);
            hotelMinimalResponse.setRatingCount(ratingCount);
            hotelMinimalResponse.setAverageRating(averageRating);
        }
        return hotelMinimalResponse;
    }

    private static HotelResponseDTO makeHotelResponseDTO(Hotel hotel) {
        HotelResponseDTO hotelResponse = new HotelResponseDTO();
        hotelResponse.setHotelId(hotel.getHotelId());
        hotelResponse.setName(hotel.getName());
        hotelResponse.setDescription(hotel.getDescription());
        hotelResponse.setLocation(hotel.getLocation());
        hotelResponse.setDefaultCheckInTime(hotel.getDefaultCheckInTime());
        hotelResponse.setDefaultCheckOutTime(hotel.getDefaultCheckOutTime());
        if (hotel.getFacilities() != null) {
            hotelResponse.setFacilities(Arrays.asList(hotel.getFacilities().split(",")));
        }
        if (hotel.getRooms() != null && hotel.getRooms().size() > 0) {
            List<RoomResponseDTO> roomResponses = hotel.getRooms().stream()
                    .filter(room -> !room.isDeleted())
                    .map(room -> {
                        RoomResponseDTO roomResponse = new RoomResponseDTO();
                        roomResponse.setRoomId(room.getRoomId());
                        roomResponse.setRoomType(room.getRoomType().getCode());
                        roomResponse.setBedType(room.getBedType().getCode());
                        roomResponse.setNumberOfAdults(room.getNumberOfAdults());
                        roomResponse.setNumberOfChildren(room.getNumberOfChildren());
                        roomResponse.setNumberOfRooms(room.getNumberOfRooms());
                        roomResponse.setBasicFare(room.getBasicFare());
                        roomResponse.setTaxPercentage(room.getTaxPercentage());
                        if (room.getFacilities() != null) {
                            roomResponse.setFacilities(Arrays.asList(room.getFacilities().split(",")));
                        }
                        return roomResponse;
                    }).collect(Collectors.toList());
            hotelResponse.setRooms(roomResponses);
        }
        List<Review> reviews = hotel.getReviews();
        if (reviews != null && reviews.size() > 0) {
            List<ReviewResponseDTO> reviewResponses = reviews.stream()
                    .filter(review -> !review.isDeleted())
                    .map(review -> {
                        ReviewResponseDTO reviewResponse = new ReviewResponseDTO();
                        reviewResponse.setReviewId(review.getReviewId());
                        reviewResponse.setComment(review.getComment());
                        reviewResponse.setRatingValue(review.getRatingValue());
                        if (review.getReviewedBy() != null) {
                            reviewResponse.setReviewedBy(review.getReviewedBy().getFullName());
                        }
                        reviewResponse.setReviewedDate(review.getReviewedDate());
                        return reviewResponse;
                    }).collect(Collectors.toList());
            hotelResponse.setReviews(reviewResponses);
            long ratingCount = reviews.stream().filter(review -> !review.isDeleted()).count();
            double averageRating = reviews.stream()
                    .filter(review -> !review.isDeleted())
                    .collect(Collectors.summarizingDouble(Review::getRatingValue))
                    .getAverage();
            hotelResponse.setRatingCount(ratingCount);
            hotelResponse.setAverageRating(averageRating);
        }
        return hotelResponse;
    }

    private List<String> getCombinedFacilities(Hotel hotel) {
        List<String> hotelFacilities = hotel.getFacilities() != null ? Arrays.asList(hotel.getFacilities().split(",")) : new ArrayList<>();
        List<String> facilities = new ArrayList<>(hotelFacilities);
        if (hotel.getRooms() != null && hotel.getRooms().size() > 0) {
            List<String> roomsFacilities = hotel.getRooms().stream()
                    .filter(room -> !room.isDeleted())
                    .map(room -> room.getFacilities() != null ? Arrays.asList(room.getFacilities().split(",")) : new ArrayList<String>())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            facilities.addAll(roomsFacilities);
        }
        return facilities;
    }

    private double getAverageRating(Hotel hotel) {
        return hotel.getReviews().stream()
                .filter(review -> !review.isDeleted())
                .collect(Collectors.summarizingDouble(Review::getRatingValue))
                .getAverage();
    }

    private long getTotalRoomsCapacity(Hotel hotel) {
        return hotel.getRooms().stream()
                .filter(room -> !room.isDeleted())
                .collect(Collectors.summarizingInt(Room::getNumberOfAdults))
                .getSum();
    }

    private long getTotalNumberOfRooms(Hotel hotel) {
        return hotel.getRooms().stream()
                .filter(room -> !room.isDeleted())
                .collect(Collectors.summarizingInt(Room::getNumberOfRooms))
                .getSum();
    }
}
