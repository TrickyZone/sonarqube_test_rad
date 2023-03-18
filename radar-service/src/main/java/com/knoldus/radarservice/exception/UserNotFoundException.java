package com.knoldus.radarservice.exception;

/** A custom exception class to throw runtime exception when the user is not available.
 *
 * @author Romit Saxena
 */
public class UserNotFoundException extends RuntimeException {

    private String statusText = "";
    private String messageKey = "";
    private transient Object[] messageArguments;

    public UserNotFoundException(String message) {

        super(message);
    }

    public UserNotFoundException() {
        // Empty. Use setters.
    }
}

