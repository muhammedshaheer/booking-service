package com.github.muhammedshaheer.bookingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO response with user information within system
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String residentialCity;
    private String contactNumber;
}
