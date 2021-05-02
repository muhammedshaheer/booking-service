package com.github.muhammedshaheer.bookingservice.service;

import com.github.muhammedshaheer.bookingservice.dto.request.AddUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.dto.request.UpdateUserRequestDTO;
import com.github.muhammedshaheer.bookingservice.entity.User;
import com.github.muhammedshaheer.bookingservice.exception.BookingServiceException;
import com.github.muhammedshaheer.bookingservice.repository.UserRepository;
import com.github.muhammedshaheer.bookingservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void test_create_user_success() {
        AddUserRequestDTO addUserRequest = new AddUserRequestDTO();
        addUserRequest.setEmail("ckshaheer2012@gmail.com");
        addUserRequest.setContactNumber("+91-9847012345");
        addUserRequest.setFirstName("Muhammed");
        addUserRequest.setLastName("Shaheer");
        userService.createUser(addUserRequest);
    }

    @Test
    public void test_update_user_invalid_user_id() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        UpdateUserRequestDTO updateUserRequest = new UpdateUserRequestDTO();
        updateUserRequest.setFirstName("Muhammed");
        updateUserRequest.setLastName("Shaheer");

        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> userService.updateUser(userId, updateUserRequest));
    }

    @Test
    public void test_update_user_inactive_user() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        UpdateUserRequestDTO updateUserRequest = new UpdateUserRequestDTO();
        updateUserRequest.setFirstName("Muhammed");
        updateUserRequest.setLastName("Shaheer");

        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dummyInactiveUserObject()));
        assertThrows(BookingServiceException.class, () -> userService.updateUser(userId, updateUserRequest));
    }

    @Test
    public void test_update_user_success() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        UpdateUserRequestDTO updateUserRequest = new UpdateUserRequestDTO();
        updateUserRequest.setFirstName("Muhammed");
        updateUserRequest.setLastName("Shaheer");

        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dummyActiveUserObject()));
        userService.updateUser(userId, updateUserRequest);
    }

    @Test
    public void test_delete_user_invalid_user_id() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> userService.deleteUser(userId));
    }

    @Test
    public void test_delete_user_inactive_user() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dummyInactiveUserObject()));
        assertThrows(BookingServiceException.class, () -> userService.deleteUser(userId));
    }

    @Test
    public void test_delete_user_success() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dummyActiveUserObject()));
        userService.deleteUser(userId);
    }

    @Test
    public void test_get_user_details_invalid_user_id() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(BookingServiceException.class, () -> userService.getUserDetails(userId));
    }

    @Test
    public void test_get_user_details_inactive_user_id() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dummyInactiveUserObject()));
        assertThrows(BookingServiceException.class, () -> userService.getUserDetails(userId));
    }

    @Test
    public void test_get_user_details_success() {
        String userId = "64eae204-5675-4402-bd9e-0abefd712f4f";
        Mockito.when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dummyActiveUserObject()));
        userService.getUserDetails(userId);
    }

    private User dummyInactiveUserObject() {
        User user = new User();
        user.setUserId("64eae204-5675-4402-bd9e-0abefd712f4f");
        user.setEmail("ckshaheer2012@gmail.com");
        user.setFirstName("Muhammed");
        user.setLastName("Shaheer");
        user.setGender("MALE");
        user.setResidentialCity("Kannur");
        user.setContactNumber("+91-9847012345");
        user.setDeleted(true);
        return user;
    }

    private User dummyActiveUserObject() {
        User user = new User();
        user.setUserId("64eae204-5675-4402-bd9e-0abefd712f4f");
        user.setEmail("ckshaheer2012@gmail.com");
        user.setFirstName("Muhammed");
        user.setLastName("Shaheer");
        user.setGender("MALE");
        user.setResidentialCity("Kannur");
        user.setContactNumber("+91-9847012345");
        user.setDeleted(false);
        return user;
    }
}
