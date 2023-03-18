package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.service.CSVService;
import liquibase.util.csv.CSVReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;

@ContextConfiguration(classes = {CSVController.class})
@ExtendWith(SpringExtension.class)
class CSVControllerTest {
    @Autowired
    private CSVController cSVController;

    @MockBean
    private CSVService cSVService;

    /**
     * Method under test: {@link CSVController#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders
                .post("/knoldus-backend/rest/radar-service/technology/csv/upload");
        CSVReader reader = new CSVReader(new FileReader("src/test/resources/dummydata/technologies.csv"));
        MockHttpServletRequestBuilder requestBuilder = postResult.param("technologies.csv", String.valueOf(reader));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cSVController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}

