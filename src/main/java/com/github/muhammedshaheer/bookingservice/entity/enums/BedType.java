package com.github.muhammedshaheer.bookingservice.entity.enums;

import com.github.muhammedshaheer.bookingservice.exception.BookingServiceException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Enum with bed types of a room
 *
 * @author Muhammed Shaheer
 */

@Getter
public enum BedType {
    STANDARD_DOUBLE("STANDARD_DOUBLE"),
    QUEEN("QUEEN"),
    KING("KING");

    private static final Logger logger = LoggerFactory.getLogger(BedType.class);

    private final String code;

    BedType(String code) {
        this.code = code;
    }

    public static BedType fromCode(String code) {
        for (BedType bedType : values()) {
            if (bedType.getCode().equals(code)) {
                return bedType;
            }
        }
        logger.error("Invalid bed type code, {}", code);
        throw new BookingServiceException("Invalid bed type code, " + code, HttpStatus.BAD_REQUEST);
    }
}
