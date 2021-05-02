package com.github.muhammedshaheer.bookingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO to return minimal response with hotel information
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelMinimalResponseDTO {
    private String hotelId;
    private String name;
    private String location;
    private Double averageRating;
    private Long ratingCount;
    private List<String> facilities;
    private List<RoomMinimalResponseDTO> rooms;
}
