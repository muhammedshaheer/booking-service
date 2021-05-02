package com.github.muhammedshaheer.bookingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO with fields to add room information for a hotel
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomRequestDTO {

    @NotBlank(message = "Roomtype is required")
    private String roomType;

    @NotBlank(message = "Bedtype is required")
    private String bedType;

    @NotNull(message = "Number of adults is required")
    private Integer numberOfAdults;

    @NotNull(message = "Number of children is required")
    private Integer numberOfChildren;

    @NotNull(message = "Number of rooms is required")
    private Integer numberOfRooms;

    @NotNull(message = "BasicFare is required")
    private Long basicFare;

    @NotNull(message = "Tax percentage is required")
    private Long taxPercentage;

    private List<String> facilities;
}
