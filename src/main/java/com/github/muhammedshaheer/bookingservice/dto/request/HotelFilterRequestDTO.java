package com.github.muhammedshaheer.bookingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO with fields to filter hotels for fetching necessary hotels for booking by an user
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilterRequestDTO {
    private String city;
    private String date;
    private Long numberOfRoomsRequired;
    private Long numberOfTravellers;
    private Double rating;
    private List<String> facilities;
    private String sortBy;
    private String sortOrder;
}
