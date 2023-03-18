package com.knoldus.radarservice.response;

import com.knoldus.radarservice.annotations.FieldDescriptorDoc;

/**
 * REST error response.
 *
 * @author Romit Saxena
 */
public class ErrorResponse<T> extends Response<T> {

    @FieldDescriptorDoc("An user-friendly description of what has occurred. Can be shown to users.")
    private String message;

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}

