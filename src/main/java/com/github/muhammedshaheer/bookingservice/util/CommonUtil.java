package com.github.muhammedshaheer.bookingservice.util;

import java.util.UUID;

/**
 * @author Muhammed Shaheer
 */

public class CommonUtil {
    public static String getGeneratedId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
