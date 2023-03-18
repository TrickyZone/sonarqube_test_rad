package com.knoldus.radarservice.exception;

/** A custom exception class to throw runtime exception when the technology is
 * not present in the database.
 */
public class TechnologyNotFoundException extends RuntimeException {
    private String statusText = "";
    private String messageKey = "";
    private transient Object[] messageArguments;

    public TechnologyNotFoundException(String message) {

        super(message);
    }

    public TechnologyNotFoundException() {
        // Empty. Use setters.
    }
}
