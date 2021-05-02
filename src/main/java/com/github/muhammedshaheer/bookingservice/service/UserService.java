package com.github.muhammedshaheer.bookingservice.service;

import com.github.muhammedshaheer.bookingservice.dto.request.AddUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.UserResponseDTO;

/**
 * @author Muhammed Shaheer
 */

public interface UserService {
    void createUser(AddUserRequestDTO addUserRequest);

    void updateUser(String userId, UpdateUserRequestDTO updateUserRequest);

    void deleteUser(String userId);

    UserResponseDTO getUserDetails(String userId);
}
