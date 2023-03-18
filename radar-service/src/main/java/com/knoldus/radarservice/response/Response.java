package com.knoldus.radarservice.response;

import com.knoldus.radarservice.annotations.FieldDescriptorDoc;

/**
 * Generic REST response.
 * <p>
 * Can be used if the payload is not generic itself.
 * <p>
 * If the payload is generic, it's better to implement {@link ApiResponse} or extend {@link BasicResponse} directly.
 *
 * @author Romit Saxena
 * @see ApiResponse
 */
public class Response<T> extends BasicResponse {

    @FieldDescriptorDoc("The response payload.")
    private T data;

    public Response<T> setStatusText(String statusText) {
        // we cast to Response<T> to be backward compatible
        return (Response<T>) super.setStatusText(statusText);
    }

    public Response<T> setStatusCode(String statusCode) {
        // we cast to Response<T> to be backward compatible
        return (Response<T>) super.setStatusCode(statusCode);
    }

    public T getData() {

        return data;
    }

    public Response<T> setData(T data) {

        this.data = data;
        return this;
    }
}
