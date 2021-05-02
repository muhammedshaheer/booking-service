package com.github.muhammedshaheer.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response wrapper which acts are wrapper to entire api responses
 *
 * @param <T>
 */

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
