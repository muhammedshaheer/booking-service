package com.github.muhammedshaheer.bookingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewRequestDTO {

    @NotBlank(message = "Comment is required")
    private String comment;

    @NotNull(message = "Rating is required")
    private Double ratingValue;

    private String userId;
}
