package com.knoldus.radarservice.exception;

/**
 *  A custom exception class to throw runtime exception when unable to upload csv file
 */
public class CSVUploadException extends RuntimeException {
    private String statusText = "";
    private String messageKey = "";
    private transient Object[] messageArguments;

    public CSVUploadException(String message) {

        super(message);
    }

    public CSVUploadException() {
        // Empty. Use setters.
    }
}
