package com.github.muhammedshaheer.bookingservice.repository;

import com.github.muhammedshaheer.bookingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to handle user entity
 *
 * @author Muhammed Shaheer
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
}
