package com.knoldus.radarservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UISupportController.class})
@ExtendWith(SpringExtension.class)
class UISupportControllerTest {
    @Autowired
    private UISupportController uISupportController;

    /**
     * Method under test: {@link UISupportController#getQuarters()}
     */
    @Test
    void testGetQuarters() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/ui-support/quarters");
        MockMvcBuilders.standaloneSetup(uISupportController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":{\"dropDownData\":[\"QUARTER_1\",\"QUARTER_2\",\"QUARTER_3\","
                                        + "\"QUARTER_4\"]}}"));
    }

    /**
     * Method under test: {@link UISupportController#getQuarters()}
     */
    @Test
    void testGetQuarters2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/ui-support/quarters", "Uri Vars");
        MockMvcBuilders.standaloneSetup(uISupportController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":{\"dropDownData\":[\"QUARTER_1\",\"QUARTER_2\",\"QUARTER_3\","
                                        + "\"QUARTER_4\"]}}"));
    }

    /**
     * Method under test: {@link UISupportController#getStudios()}
     */
    @Test
    void testGetStudios() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/ui-support/studios");
        MockMvcBuilders.standaloneSetup(uISupportController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":{\"dropDownData\":[\"JAVA_STUDIO\",\"SCALA_STUDIO\",\"DEVOPS"
                                        + "_STUDIO\",\"FRONTEND_STUDIO\",\"AI_ML_STUDIO\",\"AGILE_STUDIO\",\"RUST_STUDIO\",\"TEST_AUTOMATION_STUDIO\"]}}"));
    }

    /**
     * Method under test: {@link UISupportController#getStudios()}
     */
    @Test
    void testGetStudios2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/ui-support/studios", "Uri Vars");
        MockMvcBuilders.standaloneSetup(uISupportController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":{\"dropDownData\":[\"JAVA_STUDIO\",\"SCALA_STUDIO\",\"DEVOPS"
                                        + "_STUDIO\",\"FRONTEND_STUDIO\",\"AI_ML_STUDIO\",\"AGILE_STUDIO\",\"RUST_STUDIO\",\"TEST_AUTOMATION_STUDIO\"]}}"));
    }
}

