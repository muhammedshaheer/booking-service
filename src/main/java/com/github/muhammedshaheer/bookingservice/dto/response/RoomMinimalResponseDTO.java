package com.github.muhammedshaheer.bookingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO response with minimal room information of a hotel
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomMinimalResponseDTO {
    private String roomType;
    private Integer numberOfRooms;
    private Integer numberOfAdults;
    private Long basicFare;
    private Long taxPercentage;
    private List<String> facilities;
}
