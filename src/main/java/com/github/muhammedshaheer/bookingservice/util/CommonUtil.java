package com.github.muhammedshaheer.bookingservice.util;

import java.util.UUID;

/**
 * Util class with necessary utils within the application
 *
 * @author Muhammed Shaheer
 */

public class CommonUtil {

    /**
     * Method to generate random id using UUID
     *
     * @return generated random ID
     */
    public static String getGeneratedId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
