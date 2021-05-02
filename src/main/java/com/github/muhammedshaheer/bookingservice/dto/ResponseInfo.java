package com.github.muhammedshaheer.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.muhammedshaheer.bookingservice.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo<T> {
    private Boolean success = true;
    private T result;
    private T error;

    public ResponseInfo(T result) {
        this.result = result;
    }

    public ResponseInfo(Boolean success, T error) {
        this.success = success;
        this.error = error;
    }
}
