package com.knoldus.radarservice.exception;


/**
 * Exception thrown when a service attempts to create a technology which already exists.
 */
public class TechnologyAlreadyExistsException extends RuntimeException {

    /**
     * @param message the message assigned to the underlying {@link RuntimeException}
     */
    public TechnologyAlreadyExistsException(String message) {
        super(message);
    }

}