package com.github.muhammedshaheer.bookingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * DTO with fields to update hotel details of a hotel
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHotelRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Default Check-In time is required")
    private String defaultCheckInTime;

    @NotBlank(message = "Default Check-Out time is required")
    private String defaultCheckOutTime;

    private List<String> facilities;
}
