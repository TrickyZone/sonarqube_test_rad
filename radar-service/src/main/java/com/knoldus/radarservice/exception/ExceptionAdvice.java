package com.knoldus.radarservice.exception;

import com.knoldus.radarservice.response.ErrorResponse;
import com.knoldus.radarservice.response.ResponseStatus;
import com.knoldus.radarservice.response.Responses;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/** This is responsible to provide the custom responses when the exception or error occurred.
 *
 * @author Romit Saxena
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return Responses.error(ResponseStatus.NOT_FOUND.getText(),
                ResponseStatus.NOT_FOUND.getCode(),
                ResponseStatus.NOT_FOUND.getMessage());
    }

    // Bad Request Exception Handler
    @ExceptionHandler(value = {TechnologyAlreadyExistsException.class})
    @Order(HIGHEST_PRECEDENCE)
    public ResponseEntity<Object> handleApiRequestException(
            TechnologyAlreadyExistsException technologyAlreadyExistsException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(technologyAlreadyExistsException.getMessage(), badRequest,
                HttpServletResponse.SC_BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(TechnologyNotFoundException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleTechnologyNotFoundException(TechnologyNotFoundException technologyNotFoundException) {
        return Responses.error(ResponseStatus.NOT_FOUND.getText(),
                ResponseStatus.NOT_FOUND.getCode(),
                "Could not find the given technology");
    }

    @ExceptionHandler(CSVUploadException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCSVUploadEception(CSVUploadException csvUploadException) {
        return Responses.error(ResponseStatus.BAD_REQUEST.getText(),
                ResponseStatus.BAD_REQUEST.getCode(),
                "all or some data could not be uploaded");
    }
}
