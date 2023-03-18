package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.base.ApiTestBase;
import com.knoldus.radarservice.base.CommonTestUtils;
import com.knoldus.radarservice.service.CSVService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CSVController_ApiTest extends ApiTestBase implements CommonTestUtils {

    @MockBean
    CSVService csvService;

    @Test
    public void uploadFile_WhenValidCSVFileIsUploaded_SaveToDB() throws Exception {
        String path = "dummydata/technologies.csv";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "technologies.csv", "text/csv",readFileFromResource(path));
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(multipart("/knoldus-backend/rest/radar-service/technology/csv/upload").file(mockMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void uploadFile_WhenValidCSVFileIsUploaded_SaveToDB1() throws Exception {


        String path = "dummydata/technologies.csv";

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "technologies.csv", "text/csv",readFileFromResource(path));
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        doThrow(RuntimeException.class).when(csvService).save(mockMultipartFile);

        mockMvc.perform(multipart("/knoldus-backend/rest/radar-service/technology/csv/upload").file(mockMultipartFile))
                .andExpect(status().isBadRequest());
    }
}
