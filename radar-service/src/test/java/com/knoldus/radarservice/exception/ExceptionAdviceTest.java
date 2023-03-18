package com.knoldus.radarservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.knoldus.radarservice.response.ErrorResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ExceptionAdviceTest {
    /**
     * Method under test: {@link ExceptionAdvice#handleUserNotFoundException(UserNotFoundException)}
     */
    @Test
    void testHandleUserNotFoundException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        ErrorResponse actualHandleUserNotFoundExceptionResult = exceptionAdvice
                .handleUserNotFoundException(new UserNotFoundException("An error occurred"));
        assertEquals("Not Found", actualHandleUserNotFoundExceptionResult.getStatusText());
        assertEquals("not.found", actualHandleUserNotFoundExceptionResult.getStatusCode());
        assertEquals("User not found", actualHandleUserNotFoundExceptionResult.getMessage());
    }

    /**
     * Method under test: {@link ExceptionAdvice#handleApiRequestException(TechnologyAlreadyExistsException)}
     */
    @Test
    void testHandleApiRequestException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        ResponseEntity<Object> actualHandleApiRequestExceptionResult = exceptionAdvice
                .handleApiRequestException(new TechnologyAlreadyExistsException("An error occurred"));
        assertTrue(actualHandleApiRequestExceptionResult.hasBody());
        assertTrue(actualHandleApiRequestExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualHandleApiRequestExceptionResult.getStatusCode());
        assertEquals("An error occurred", ((ApiException) actualHandleApiRequestExceptionResult.getBody()).getMessage());
        assertEquals(400, ((ApiException) actualHandleApiRequestExceptionResult.getBody()).getCode());
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiException) actualHandleApiRequestExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test: {@link ExceptionAdvice#handleTechnologyNotFoundException(TechnologyNotFoundException)}
     */
    @Test
    void testHandleTechnologyNotFoundException() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        ErrorResponse actualHandleTechnologyNotFoundExceptionResult = exceptionAdvice
                .handleTechnologyNotFoundException(new TechnologyNotFoundException("An error occurred"));
        assertEquals("Not Found", actualHandleTechnologyNotFoundExceptionResult.getStatusText());
        assertEquals("not.found", actualHandleTechnologyNotFoundExceptionResult.getStatusCode());
        assertEquals("Could not find the given technology", actualHandleTechnologyNotFoundExceptionResult.getMessage());
    }

    /**
     * Method under test: {@link ExceptionAdvice#handleCSVUploadEception(CSVUploadException)}
     */
    @Test
    void testHandleCSVUploadEception() {
        ExceptionAdvice exceptionAdvice = new ExceptionAdvice();
        ErrorResponse actualHandleCSVUploadEceptionResult = exceptionAdvice
                .handleCSVUploadEception(new CSVUploadException("An error occurred"));
        assertEquals("bad request", actualHandleCSVUploadEceptionResult.getStatusText());
        assertEquals("400", actualHandleCSVUploadEceptionResult.getStatusCode());
        assertEquals("all or some data could not be uploaded", actualHandleCSVUploadEceptionResult.getMessage());
    }
}

