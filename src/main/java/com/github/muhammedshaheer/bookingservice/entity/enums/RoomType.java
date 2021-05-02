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
public enum RoomType {
    SINGLE("SINGLE"),
    DOUBLE("DOUBLE"),
    DOUBLE_DOUBLE("DOUBLE_DOUBLE"),
    TWIN("TWIN"),
    INTERCONNECTING("INTERCONNECTING"),
    DUPLEX("DUPLEX"),
    SUIT("SUIT");

    private static final Logger logger = LoggerFactory.getLogger(RoomType.class);

    private final String code;

    RoomType(String code) {
        this.code = code;
    }

    public static RoomType fromCode(String code) {
        for (RoomType roomType : values()) {
            if (roomType.getCode().equals(code)) {
                return roomType;
            }
        }
        logger.error("Invalid room type code, {}", code);
        throw new BookingServiceException("Invalid room type code, " + code, HttpStatus.BAD_REQUEST);
    }
}
