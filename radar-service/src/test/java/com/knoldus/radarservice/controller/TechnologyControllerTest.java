package com.knoldus.radarservice.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.radarservice.model.QuarterType;
import com.knoldus.radarservice.model.StudioType;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.model.TechnologyRequest;
import com.knoldus.radarservice.model.UpdateTechnologyRequest;
import com.knoldus.radarservice.service.TechnologyService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TechnologyController.class})
@ExtendWith(SpringExtension.class)
class TechnologyControllerTest {
    @Autowired
    private TechnologyController technologyController;

    @MockBean
    private TechnologyService technologyService;

    /**
     * Method under test: {@link TechnologyController#createTechnology(TechnologyRequest)}
     */
    @Test
    void testCreateTechnology() throws Exception {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("Alternate Technology");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("Name");
        technology.setQuadrant("Quadrant");
        technology.setQuarter("Quarter");
        technology.setRing("Ring");
        technology.setStudio("Studio");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("Year");
        when(technologyService.createTechnology((TechnologyRequest) any())).thenReturn(technology);

        TechnologyRequest technologyRequest = new TechnologyRequest();
        technologyRequest.setActive(true);
        technologyRequest.setAlternateTechnology("Alternate Technology");
        technologyRequest.setDescription("");
        technologyRequest.setGithubUrl("https://example.org/example");
        technologyRequest.setIsNew(true);
        technologyRequest.setName("Name");
        technologyRequest.setQuadrant("Quadrant");
        technologyRequest.setQuarter(QuarterType.QUARTER_1);
        technologyRequest.setRing("Ring");
        technologyRequest.setStudio(StudioType.JAVA_STUDIO);
        technologyRequest.setYear("Year");
        String content = (new ObjectMapper()).writeValueAsString(technologyRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/knoldus-backend/rest/radar-service/technology")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link TechnologyController#createTechnologies(List)}
     */
    @Test
    void testCreateTechnologies() throws Exception {
        when(technologyService.createTechnologies((List<TechnologyRequest>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/knoldus-backend/rest/radar-service/technology/creates")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"statusText\":\"Successfully saved all technologies\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar() throws Exception {
        when(technologyService.getTechnologyForRadar((String) any(), (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/radar");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar2() throws Exception {
        when(technologyService.getTechnologyForRadar((String) any(), (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/radar");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnology()}
     */
    @Test
    void testGetTechnology() throws Exception {
        when(technologyService.getTechnology()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnology()}
     */
    @Test
    void testGetTechnology2() throws Exception {
        when(technologyService.getTechnology()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getAllStudio()}
     */
    @Test
    void testGetAllStudio() throws Exception {
        when(technologyService.getAllStudio()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/studio");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getAllStudio()}
     */
    @Test
    void testGetAllStudio2() throws Exception {
        when(technologyService.getAllStudio()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/studio");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getAllQuarter()}
     */
    @Test
    void testGetAllQuarter() throws Exception {
        when(technologyService.getAllQuarter()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/quarter");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getAllQuarter()}
     */
    @Test
    void testGetAllQuarter2() throws Exception {
        when(technologyService.getAllQuarter()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/quarter");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#deleteResponse(UUID)}
     */
    @Test
    void testDeleteResponse() throws Exception {
        doNothing().when(technologyService).delete((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/knoldus-backend/rest/radar-service/technology/delete/{technologyId}", UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":null}"));
    }

    /**
     * Method under test: {@link TechnologyController#deleteResponse(UUID)}
     */
    @Test
    void testDeleteResponse2() throws Exception {
        doNothing().when(technologyService).delete((UUID) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link TechnologyController#deleteResponse(UUID)}
     */
    @Test
    void testDeleteResponse3() throws Exception {
        doNothing().when(technologyService).delete((UUID) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
                .delete("/knoldus-backend/rest/radar-service/technology/delete/{technologyId}", UUID.randomUUID());
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":null}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testGetTechnologyWithRespectOfAreaAndQuadrantAndStudio() throws Exception {
        when(technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/techfilter");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testGetTechnologyWithRespectOfAreaAndQuadrantAndStudio2() throws Exception {
        when(technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/techfilter");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnologyWithRespectOfName(String)}
     */
    @Test
    void testGetTechnologyWithRespectOfName() throws Exception {
        when(technologyService.fetchTechnologyWithRespectOfName((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/name");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }

    /**
     * Method under test: {@link TechnologyController#getTechnologyWithRespectOfName(String)}
     */
    @Test
    void testGetTechnologyWithRespectOfName2() throws Exception {
        when(technologyService.fetchTechnologyWithRespectOfName((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/knoldus-backend/rest/radar-service/technology/name");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(technologyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"statusText\":\"ok\",\"statusCode\":\"ok\",\"data\":[]}"));
    }
}

