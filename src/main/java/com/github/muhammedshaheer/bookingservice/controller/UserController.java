package com.github.muhammedshaheer.bookingservice.controller;

import com.github.muhammedshaheer.bookingservice.dto.ResponseInfo;
import com.github.muhammedshaheer.bookingservice.dto.request.AddUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.response.UserResponseDTO;
import com.github.muhammedshaheer.bookingservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller class to handle user related APIs
 *
 * @author Muhammed Shaheer
 */

@RequestMapping("/users")
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to handle user creation
     *
     * @param addUserRequest DTO with necessary user information to be created
     * @return success value as true/false
     */
    @PostMapping
    public ResponseInfo<?> createUser(@Valid @RequestBody AddUserRequestDTO addUserRequest) {
        logger.info("Request received to add a new user information");
        userService.createUser(addUserRequest);
        return new ResponseInfo<>();
    }

    /**
     * Endpoint to update user information within system
     *
     * @param userId            UserId for which user information to be updated
     * @param updateUserRequest DTO with necessary user information that are to be updated
     * @return success value as true/false
     */
    @PutMapping("/{userId}")
    public ResponseInfo<?> updateUser(@PathVariable(value = "userId") String userId,
                                      @Valid @RequestBody UpdateUserRequestDTO updateUserRequest) {
        logger.info("Request received to update an user information | userId:{}", userId);
        userService.updateUser(userId, updateUserRequest);
        return new ResponseInfo<>();
    }

    /**
     * Endpoint to delete user information
     *
     * @param userId UserId for which user information to be deleted
     * @return success value as true/false
     */
    @DeleteMapping("/{userId}")
    public ResponseInfo<?> deleteUser(@PathVariable("userId") String userId) {
        logger.info("Request received to delete an user information | userId:{}", userId);
        userService.deleteUser(userId);
        return new ResponseInfo<>();
    }

    /**
     * Endpoint to fetch user details
     *
     * @param userId UserId for which user information to be fetched
     * @return response object with user details
     */
    @GetMapping("/{userId}")
    public ResponseInfo<?> getUserDetails(@PathVariable("userId") String userId) {
        logger.info("Request received to fetch user information | userId:{}", userId);
        UserResponseDTO userResponse = userService.getUserDetails(userId);
        return new ResponseInfo<>(userResponse);
    }
}
