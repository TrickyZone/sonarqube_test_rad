package com.knoldus.radarservice.response;

/**
 * Utilities for handling responses.
 *
 * @author Romit Saxena
 */
public class Responses {

    /**
     * @return A basic HTTP OK response without payload data.
     */
    public static Response ok() {

        Response response = new Response();
        response.setStatusCode(ResponseStatus.OK.getCode());
        response.setStatusText(ResponseStatus.OK.getText());

        return response;
    }

    /**
     * @param <T> This is the type parameter
     * @param payload The data to return to the client
     * @return A basic HTTP OK response with the payload data.
     */
    public static <T> Response<T> ok(T payload) {

        Response<T> response = new Response<>();
        response.setStatusCode(ResponseStatus.OK.getCode());
        response.setStatusText(ResponseStatus.OK.getText());
        response.setData(payload);

        return response;
    }

    /**
     * @param statusText The developer friendly error message.
     * @param code The error code.
     * @param message The user friendly error message.
     * @return An error response with the supplied parameters.
     */
    public static ErrorResponse error(String statusText, String code, String message) {

        ErrorResponse response = new ErrorResponse();

        response.setStatusCode(code);
        response.setStatusText(statusText);
        response.setMessage(message);

        return response;
    }
}

