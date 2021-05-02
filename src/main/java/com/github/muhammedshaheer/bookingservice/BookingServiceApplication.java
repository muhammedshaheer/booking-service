package com.github.muhammedshaheer.bookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Booking application start point
 *
 * @author Muhammed Shaheer
 */

@SpringBootApplication
@EnableJpaRepositories
public class BookingServiceApplication {

    /**
     * Main method to start the booking applications
     *
     * @param args arguments passed to the main methodO
     */
    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

}
