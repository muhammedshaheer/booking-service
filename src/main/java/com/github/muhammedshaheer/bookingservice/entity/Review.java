package com.github.muhammedshaheer.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Entity with review information of a hotel
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reviewId;

    @ManyToOne
    @JoinColumn(name = "hotel")
    private Hotel hotel;

    private String comment;
    private Double ratingValue;

    @ManyToOne
    @JoinColumn(name = "reviewedBy", referencedColumnName = "id")
    private User reviewedBy;

    private Date reviewedDate;
    private boolean deleted;
}
