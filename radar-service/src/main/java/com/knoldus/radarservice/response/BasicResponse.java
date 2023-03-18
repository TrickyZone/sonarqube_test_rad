package com.knoldus.radarservice.response;

import com.knoldus.radarservice.annotations.FieldDescriptorDoc;

/**
 * Basic API response that returns {@link ResponseStatus#OK}.
 *
 * @author Romit Saxena
 */
public class BasicResponse implements ApiResponse {

    @FieldDescriptorDoc("Brief developer-friendly description of the status. Do not show to users.")
    private String statusText;

    @FieldDescriptorDoc("The code that is returned as a response. " +
            "Note that there is no one-to-one mapping between HTTP status codes and this code. " +
            "Do not show to users.")
    private String statusCode;

    public BasicResponse() {

        this.statusText = ResponseStatus.OK.getText();
        this.statusCode = ResponseStatus.OK.getCode();
    }

    @Override
    public String getStatusText() {

        return statusText;
    }

    public BasicResponse setStatusText(String statusText) {

        this.statusText = statusText;
        return this;
    }

    @Override
    public String getStatusCode() {

        return statusCode;
    }

    public BasicResponse setStatusCode(String statusCode) {

        this.statusCode = statusCode;
        return this;
    }
}

