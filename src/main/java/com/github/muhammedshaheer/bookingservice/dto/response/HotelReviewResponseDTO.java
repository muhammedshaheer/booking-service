package com.github.muhammedshaheer.bookingservice.dto.response;

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
public class HotelReviewResponseDTO {
    private Double averageRating;
    private Long ratingCount;
    private List<ReviewResponseDTO> reviews;
}
