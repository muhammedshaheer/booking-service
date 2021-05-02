package com.github.muhammedshaheer.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Entity with hotel information
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String hotelId;
    private String name;
    private String description;
    private String location;
    private String defaultCheckInTime;
    private String defaultCheckOutTime;
    private String facilities;

    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel")
    private List<Booking> bookings;

    private Date createdDate;
    private Date lastModifiedDate;
    private boolean deleted;
}
