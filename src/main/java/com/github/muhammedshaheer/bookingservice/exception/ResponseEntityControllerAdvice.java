package com.github.muhammedshaheer.bookingservice.exception;

import com.github.muhammedshaheer.bookingservice.dto.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.muhammedshaheer.bookingservice.constant.Constant.DEFAULT_ERROR_MESSAGE;

/**
 * Controller advice class to return custom exceptions that are thrown within the application
 *
 * @author Muhammed Shaheer
 */

@ControllerAdvice
public class ResponseEntityControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseEntityControllerAdvice.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<String> errorMessages = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        logger.error("Mandatory fields missing in request | requestURI:{},missingFields:{}", requestURI, errorMessages);
        ResponseInfo<List<String>> responseInfo = new ResponseInfo<>(false, errorMessages);
        return new ResponseEntity<>(responseInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingServiceException.class)
    public ResponseEntity<?> handleBookingServiceException(BookingServiceException bookingServiceException, WebRequest webRequest) {
        HttpStatus httpStatus = bookingServiceException.getHttpStatus() != null ? bookingServiceException.getHttpStatus() : HttpStatus.OK;
        String message = bookingServiceException.getMessage() != null ? bookingServiceException.getMessage() : DEFAULT_ERROR_MESSAGE;
        ResponseInfo<String> responseInfo = new ResponseInfo<>(false, message);
        return new ResponseEntity<>(responseInfo, httpStatus);
    }
}
