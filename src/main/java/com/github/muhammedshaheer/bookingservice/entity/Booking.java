package com.github.muhammedshaheer.bookingservice.entity;

import com.github.muhammedshaheer.bookingservice.entity.converter.BookingStatusConverter;
import com.github.muhammedshaheer.bookingservice.entity.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Entity with booking information
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bookingId;

    @ManyToOne
    @JoinColumn(name = "hotel")
    private Hotel hotel;

    @OneToOne
    @JoinColumn(name = "room", referencedColumnName = "roomId")
    private Room room;

    private Date fromDate;
    private Date toDate;

    @Convert(converter = BookingStatusConverter.class)
    private BookingStatus bookingStatus;

    private int numberOfRooms;
    private int numberOfAdults;
    private int numberOfChildren;
}
