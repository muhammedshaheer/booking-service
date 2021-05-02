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

    @PostMapping("")
    public ResponseInfo<?> createUser(@Valid @RequestBody AddUserRequestDTO addUserRequest) {
        logger.info("Request received to add a new user information");
        userService.createUser(addUserRequest);
        return new ResponseInfo<>();
    }

    @PutMapping("/{userId}")
    public ResponseInfo<?> updateUser(@PathVariable(value = "userId") String userId,
                                      @Valid @RequestBody UpdateUserRequestDTO updateUserRequest) {
        logger.info("Request received to update an user information | userId:{}", userId);
        userService.updateUser(userId, updateUserRequest);
        return new ResponseInfo<>();
    }

    @DeleteMapping("/{userId}")
    public ResponseInfo<?> deleteUser(@PathVariable("userId") String userId) {
        logger.info("Request received to delete an user information | userId:{}", userId);
        userService.deleteUser(userId);
        return new ResponseInfo<>();
    }

    @GetMapping("/{userId}")
    public ResponseInfo<?> getUserDetails(@PathVariable("userId") String userId) {
        logger.info("Request received to fetch user information | userId:{}", userId);
        UserResponseDTO userResponse = userService.getUserDetails(userId);
        return new ResponseInfo<>(userResponse);
    }
}
