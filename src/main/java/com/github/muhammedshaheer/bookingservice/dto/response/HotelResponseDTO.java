package com.github.muhammedshaheer.bookingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelResponseDTO {
    private String hotelId;
    private String name;
    private String description;
    private String location;
    private String defaultCheckInTime;
    private String defaultCheckOutTime;
    private Double averageRating;
    private Long ratingCount;
    private List<String> facilities;
    private List<RoomResponseDTO> rooms;
    private List<ReviewResponseDTO> reviews;
}
