package com.example.jvspringboottestcontainer.exception;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class ApiExceptionDto {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;
    private Exception exception;
}
