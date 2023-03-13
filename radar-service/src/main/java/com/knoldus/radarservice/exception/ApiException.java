package com.knoldus.radarservice.exception;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException
{

    private final String message;

    private final HttpStatus status;

    private final int code;

    private final ZonedDateTime zoneDateTime;
    

}
