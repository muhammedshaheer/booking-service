package com.github.muhammedshaheer.bookingservice.controller;

import com.github.muhammedshaheer.bookingservice.dto.ResponseInfo;
import com.github.muhammedshaheer.bookingservice.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Muhammed Shaheer
 */

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public ResponseInfo<?> getUserDetails() {
        String generatedId = CommonUtil.getGeneratedId();
        logger.info("Test connection {}", generatedId);
        return new ResponseInfo<>(generatedId);
    }
}
