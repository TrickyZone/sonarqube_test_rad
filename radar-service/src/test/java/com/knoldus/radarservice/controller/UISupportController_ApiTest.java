package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.base.ApiTestBase;
import com.knoldus.radarservice.model.StudioType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UISupportController_ApiTest extends ApiTestBase {

    private static final String GET_STUDIOS_ENDPOINT = "/knoldus-backend/rest/radar-service/ui-support/studios";
    private static final String GET_QUARTER_ENDPOINT = "/knoldus-backend/rest/radar-service/ui-support/quarters";
    private static final String APPLICATION_JSON_VALUE = "application/json";

    @Test
    void getStudio_returnsWithSuccess() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GET_STUDIOS_ENDPOINT)
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }

    @Test
    void getQuarter_returnsWithSuccess() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GET_QUARTER_ENDPOINT)
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }
}
