package com.job.api.util;

import com.job.api.domain.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static <T> ResponseEntity<Object> buildResponse(String responseKey, String responseMessage, T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(ApiResponse.builder()
                .responseKey(responseKey)
                .responseMessage(responseMessage)
                .data(data)
                .build(), httpStatus);
    }

}
