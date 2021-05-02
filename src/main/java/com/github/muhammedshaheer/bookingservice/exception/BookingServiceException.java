package com.github.muhammedshaheer.bookingservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Muhammed Shaheer
 */

@Getter
@Setter
public class BookingServiceException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public BookingServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BookingServiceException(String message) {
        super(message);
        this.message = message;
    }

    public BookingServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BookingServiceException(Throwable cause) {
        super(cause);
    }

    public BookingServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }
}
