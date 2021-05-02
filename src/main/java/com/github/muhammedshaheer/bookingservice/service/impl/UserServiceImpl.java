package com.github.muhammedshaheer.bookingservice.service.impl;

import com.github.muhammedshaheer.bookingservice.dto.request.AddUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.UserResponseDTO;
import com.github.muhammedshaheer.bookingservice.entity.User;
import com.github.muhammedshaheer.bookingservice.exception.BookingServiceException;
import com.github.muhammedshaheer.bookingservice.repository.UserRepository;
import com.github.muhammedshaheer.bookingservice.service.UserService;
import com.github.muhammedshaheer.bookingservice.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Optional;

/**
 * @author Muhammed Shaheer
 */

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(AddUserRequestDTO addUserRequest) {
        User user = new User();
        String generatedUserId = CommonUtil.getGeneratedId();
        Date creationDate = new Date();
        user.setUserId(generatedUserId);
        user.setEmail(addUserRequest.getEmail());
        user.setFirstName(addUserRequest.getFirstName());
        user.setLastName(addUserRequest.getLastName());
        user.setGender(addUserRequest.getGender());
        user.setResidentialCity(addUserRequest.getResidentialCity());
        user.setContactNumber(addUserRequest.getContactNumber());
        user.setCreatedDate(creationDate);
        user.setLastModifiedDate(creationDate);
        user.setDeleted(false);

        userRepository.save(user);
        logger.info("Created user information successfully | userId:{}", generatedUserId);
    }

    @Override
    public void updateUser(String userId, UpdateUserRequestDTO updateUserRequest) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        User user = optionalUser.orElseThrow(() -> {
            logger.error("User information not found | userId:{}", userId);
            return new BookingServiceException("User information not found | userId:" + userId);
        });
        if (user.isDeleted()) {
            logger.info("User is inactive | userId:{}", userId);
            throw new BookingServiceException("User is inactive | userId:" + userId);
        }
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        if (!ObjectUtils.isEmpty(updateUserRequest.getContactNumber())) {
            user.setContactNumber(updateUserRequest.getContactNumber());
        }
        if (!ObjectUtils.isEmpty(updateUserRequest.getResidentialCity())) {
            user.setResidentialCity(updateUserRequest.getResidentialCity());
        }
        Date lastModifiedDate = new Date();
        user.setLastModifiedDate(lastModifiedDate);
        userRepository.save(user);
        logger.info("Updated user information successfully | userId:{}", userId);
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        User user = optionalUser.orElseThrow(() -> {
            logger.error("User information not found | userId:{}", userId);
            return new BookingServiceException("User information not found | userId:" + userId);
        });
        if (user.isDeleted()) {
            logger.info("User is inactive | userId:{}", userId);
            throw new BookingServiceException("User is inactive | userId:" + userId);
        }
        user.setDeleted(true);
        Date lastModifiedDate = new Date();
        user.setLastModifiedDate(lastModifiedDate);
        userRepository.save(user);
        logger.info("Deleted user information successfully | userId:{}", userId);
    }

    @Override
    public UserResponseDTO getUserDetails(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        User user = optionalUser.orElseThrow(() -> {
            logger.error("User information not found | userId:{}", userId);
            return new BookingServiceException("User information not found | userId:" + userId);
        });
        if (user.isDeleted()) {
            logger.info("User is inactive | userId:{}", userId);
            throw new BookingServiceException("User is inactive | userId:" + userId);
        }
        logger.info("Fetched user information successfully | userId:{}", userId);
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setGender(user.getGender());
        userResponse.setResidentialCity(user.getResidentialCity());
        userResponse.setContactNumber(user.getContactNumber());
        return userResponse;
    }
}
