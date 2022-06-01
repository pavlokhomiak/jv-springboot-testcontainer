package com.example.jvspringboottestcontainer.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionDto> handleException(Exception ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto()
                .setHttpStatus(badRequest)
                .setMessage(ex.getMessage())
                .setZonedDateTime(ZonedDateTime.now(ZoneId.of("Europe/Kiev")));
        return new ResponseEntity<>(apiExceptionDto, badRequest);
    }
}
