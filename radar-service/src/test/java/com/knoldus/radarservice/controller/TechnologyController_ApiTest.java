package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.base.ApiTestBase;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.exception.TechnologyAlreadyExistsException;
import com.knoldus.radarservice.exception.TechnologyNotFoundException;
import com.knoldus.radarservice.service.TechnologyService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.knoldus.radarservice.model.QuarterType.QUARTER_2;
import static com.knoldus.radarservice.model.QuarterType.QUARTER_4;
import static org.mockito.Mockito.*;

class TechnologyController_ApiTest extends ApiTestBase {

    private static final String TECHNOLOGY_CONTROLLER_ENDPOINT = "/knoldus-backend/rest/radar-service/technology";
    private static final String GET_TECHNOLOGY_RADAR_CONTROLLER_ENDPOINT = "/knoldus-backend/rest/radar-service/technology/radar";
    private static final String UPDATE_TECHNOLOGY_ENDPOINT = "/knoldus-backend/rest/radar-service/technology/update";
    private static final String DELETE_TECHNOLOGY_ENDPOINT = "/knoldus-backend/rest/radar-service/technology/delete/40bb5bdf-80d2-47b5-a615-178e14c65760";
    private static final String GET_TECHNOLOGY_QUARTERLY_ENDPOINT = "/knoldus-backend/rest/radar-service/technology";

    private static final String GET_TECHNOLOGY_NAME = "/knoldus-backend/rest/radar-service/technology/name";
    private static final String GET_TECHNOLOGY_BASED_ON_AREA_QUADRANT_AND_STUDIO = "/knoldus-backend/rest/radar-service/technology/";

    private static final String APPLICATION_JSON_VALUE = "application/json";

    @MockBean
    private TechnologyService mockTechnologyService;

    @Autowired
    private TechnologyController technologyController;

    @BeforeEach
    protected void setup() {
        super.setup();
        reset(mockTechnologyService);
    }

    @Test
    void getTechnologyForRadar_returnsWithSuccess() {

        Technology technology1 = technologyFixture(UUID.randomUUID());
        Technology technology2 = technologyFixture(UUID.randomUUID(),
                "JAVA-11",
                "1",
                "secure",
                "Java Studio",
                QUARTER_2);

        List<Technology> technologies = List.of(technology1, technology2);

        doReturn(technologies).when(mockTechnologyService).getTechnologyForRadar("", "");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GET_TECHNOLOGY_RADAR_CONTROLLER_ENDPOINT)
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }

    @Test
    void createTechnology_InsertNewTechnology() {

        UUID uuid = UUID.randomUUID();
        var technology = technologyFixture(uuid);

        doReturn(technology).when(mockTechnologyService).createTechnology(TECHNOLOGY_REQUEST);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(TECHNOLOGY_CONTROLLER_ENDPOINT)
                .content(toJson(TECHNOLOGY_REQUEST))
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }

    @Test
    void createTechnology_ThrowsException_WhenSameTechnologyExist() {

        when(mockTechnologyService.createTechnology(TECHNOLOGY_REQUEST))
                .thenThrow(TechnologyAlreadyExistsException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(TECHNOLOGY_CONTROLLER_ENDPOINT)
                .content(toJson(TECHNOLOGY_REQUEST))
                .contentType(APPLICATION_JSON_VALUE);

        validateBadRequest(request);
    }

    @Test
    void updateTechnology_UpdateTechnology_WhenDataExistInDatabase() {

        var technology = technologyFixture(TECHNOLOGY_ID);
        when(mockTechnologyService.update(UPDATE_TECHNOLOGY_REQUEST)).thenReturn(technology);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(UPDATE_TECHNOLOGY_ENDPOINT)
                .content(toJson(UPDATE_TECHNOLOGY_REQUEST))
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }

    @Test
    void updateTechnology_ThrowsException_WhenDataDoesNotExistInDatabase() {

        when(mockTechnologyService.update(UPDATE_TECHNOLOGY_REQUEST))
                .thenThrow(TechnologyNotFoundException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(UPDATE_TECHNOLOGY_ENDPOINT)
                .content(toJson(UPDATE_TECHNOLOGY_REQUEST))
                .contentType(APPLICATION_JSON_VALUE);

        validate404NotFound(request);
    }

    @Test
    void deleteTechnology_DeleteTechnology_WhenDataExistInDatabase() {

        doNothing().when(mockTechnologyService).delete(UUID.fromString("40bb5bdf-80d2-47b5-a615-178e14c65760"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(DELETE_TECHNOLOGY_ENDPOINT)
                .content(toJson(TECHNOLOGY_REQUEST))
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequestWithEmptyData(request);
    }

    @Test
    void deleteTechnology_DeleteTechnology_WhenDataDoesNotExistInDatabase() {

        doThrow(TechnologyNotFoundException.class).when(mockTechnologyService)
                .delete(UUID.fromString("40bb5bdf-80d2-47b5-a615-178e14c65760"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(DELETE_TECHNOLOGY_ENDPOINT)
                .content(toJson(TECHNOLOGY_REQUEST))
                .contentType(APPLICATION_JSON_VALUE);

        validate404NotFound(request);
    }

    @Test
    void getTechnology_returnsDataWithSuccess() {

        Technology technology1 = technologyFixture(UUID.randomUUID());
        Technology technology2 = technologyFixture(UUID.randomUUID(),
                "JAVA-11",
                "1",
                "robust and secure",
                "Java Studio",
                QUARTER_4);

        List<Technology> technologies = List.of(technology1, technology2);

        doReturn(technologies).when(mockTechnologyService).getTechnology();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GET_TECHNOLOGY_QUARTERLY_ENDPOINT)
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }

    @Test
    void getTechnology_returnsDataWithSuccess11() {

        Technology technology1 = technologyFixture(UUID.randomUUID());
        Technology technology2 = technologyFixture(UUID.randomUUID(),
                "JAVA-11",
                "1",
                "robust and secure",
                "Java Studio",
                QUARTER_4);

        List<Technology> technologies = List.of(technology1, technology2);

        doReturn(technologies).when(mockTechnologyService).fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio("Area","Quarter","Studio");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GET_TECHNOLOGY_BASED_ON_AREA_QUADRANT_AND_STUDIO)
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }

    @Test
    void getTechnologyName_returnsDataWithSuccess() {

        Technology technology1 = technologyFixture(UUID.randomUUID());
        Technology technology2 = technologyFixture(UUID.randomUUID(),
                "JAVA-11",
                "1",
                "robust and secure",
                "Java Studio",
                QUARTER_4);

        List<Technology> technologies = List.of(technology1, technology2);

        doReturn(technologies).when(mockTechnologyService).fetchTechnologyWithRespectOfName("name");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GET_TECHNOLOGY_NAME)
                .contentType(APPLICATION_JSON_VALUE);

        validate200OKRequest(request);
    }



}
