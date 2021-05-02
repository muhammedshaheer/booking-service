package com.github.muhammedshaheer.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Entity with user information
 *
 * @author Muhammed Shaheer
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;
    private String lastName;
    private String gender;
    private String residentialCity;
    private String contactNumber;
    private Date createdDate;
    private Date lastModifiedDate;
    private boolean deleted;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
