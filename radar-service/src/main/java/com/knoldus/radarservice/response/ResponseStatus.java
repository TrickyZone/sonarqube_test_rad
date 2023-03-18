package com.knoldus.radarservice.response;

/**
 * Response statuses.
 *
 * @author Romit Saxena
 */
public enum ResponseStatus {
    NOT_FOUND("not.found", "Not Found", "User not found"),
    OK("ok", "ok", "Success"),
    BAD_REQUEST("400", "bad request", "Please Check the request made"),
    SERVICE_ERROR("5xx", "service error", "There is some problem in the service");

    private String code;
    private String text;
    private String message;

    ResponseStatus(String code, String text, String message) {

        this.code = code;
        this.text = text;
        this.message = message;
    }

    public String getCode() {

        return code;
    }

    public String getText() {

        return text;
    }

    public String getMessage() {

        return message;
    }
}


