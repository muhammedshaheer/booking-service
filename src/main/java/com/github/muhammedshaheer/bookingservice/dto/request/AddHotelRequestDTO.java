package com.github.muhammedshaheer.bookingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * DTO with fields to create hotel information
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHotelRequestDTO {

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

    @NotEmpty(message = "Please provide room details")
    private List<AddRoomRequestDTO> rooms;
}
