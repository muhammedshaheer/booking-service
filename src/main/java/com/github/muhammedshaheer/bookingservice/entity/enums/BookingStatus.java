package com.github.muhammedshaheer.bookingservice.entity.enums;

import com.github.muhammedshaheer.bookingservice.exception.BookingServiceException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * @author Muhammed Shaheer
 */

@Getter
public enum BookingStatus {
    RESERVED("RESERVED"),
    CONFIRMED("CONFIRMED"),
    CANCELLED("CANCELLED");

    private static final Logger logger = LoggerFactory.getLogger(BookingStatus.class);

    private final String code;

    BookingStatus(String code) {
        this.code = code;
    }

    public static BookingStatus fromCode(String code) {
        for (BookingStatus bookingStatus : values()) {
            if (bookingStatus.getCode().equals(code)) {
                return bookingStatus;
            }
        }
        logger.error("Invalid booking status code, {}", code);
        throw new BookingServiceException("Invalid booking status code, " + code, HttpStatus.BAD_REQUEST);
    }
}
