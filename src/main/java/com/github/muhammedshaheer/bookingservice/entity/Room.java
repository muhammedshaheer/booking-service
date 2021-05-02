package com.github.muhammedshaheer.bookingservice.entity;

import com.github.muhammedshaheer.bookingservice.entity.converter.BedTypeConverter;
import com.github.muhammedshaheer.bookingservice.entity.converter.RoomTypeConverter;
import com.github.muhammedshaheer.bookingservice.entity.enums.BedType;
import com.github.muhammedshaheer.bookingservice.entity.enums.RoomType;
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
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roomId;

    @Convert(converter = RoomTypeConverter.class)
    private RoomType roomType;

    @Convert(converter = BedTypeConverter.class)
    private BedType bedType;

    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private Integer numberOfRooms;
    private Long basicFare;
    private Long taxPercentage;
    private String facilities;
    private Date createdDate;
    private Date lastModifiedDate;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "hotel")
    private Hotel hotel;
}
