package com.knoldus.radarservice.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.radarservice.annotations.ApiTest;
import com.knoldus.radarservice.config.ApiTestConfig;
import com.knoldus.radarservice.model.QuarterType;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.model.TechnologyRequest;
import com.knoldus.radarservice.model.UpdateTechnologyRequest;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.knoldus.radarservice.model.QuarterType.QUARTER_1;
import static com.knoldus.radarservice.model.StudioType.JAVA_STUDIO;

@ExtendWith(SpringExtension.class)
@ApiTest(config = ApiTestConfig.class)
public class ApiTestBase {

    protected static final UUID TECHNOLOGY_ID = UUID.fromString("bd458343-3108-48ac-b8c0-7334e78e55fc");
    protected static final String TECHNOLOGY_NAME = "java";
    protected static final String TECHNOLOGY_RING = "assess";
    protected static final String TECHNOLOGY_QUADRANT = "Languages and Frameworks";
    protected static final String TECHNOLOGY_DESCRIPTION = "Java is a plausible choice for new products (due to its rich ecosystem)";
    protected static final String YEAR = "2022";
    protected static final TechnologyRequest TECHNOLOGY_REQUEST = new TechnologyRequest(TECHNOLOGY_NAME,
            TECHNOLOGY_RING,
            TECHNOLOGY_QUADRANT,
            true,
            TECHNOLOGY_DESCRIPTION,
            JAVA_STUDIO,
            QUARTER_1,
            true,
            YEAR,
            null,
            null
    );
    protected static final UpdateTechnologyRequest UPDATE_TECHNOLOGY_REQUEST = new UpdateTechnologyRequest(
            TECHNOLOGY_ID,
            TECHNOLOGY_NAME,
            TECHNOLOGY_RING,
            TECHNOLOGY_QUADRANT,
            true,
            TECHNOLOGY_DESCRIPTION,
            JAVA_STUDIO,
            QUARTER_1,
            true,
            YEAR);


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @BeforeEach
    protected void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    protected String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't convert object to JSON", e);
        }
    }

    protected void validate200OKRequest(MockHttpServletRequestBuilder request) {
        try {
            var temp = mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusText").value("ok"))
                    .andExpect(jsonPath("$.statusCode").value("ok"))
                    .andExpect(jsonPath("$.data").exists());

        } catch (Exception e) {
            throw new RuntimeException("Failed to validate API", e);
        }
    }

    protected void validate200OKRequestWithEmptyData(MockHttpServletRequestBuilder request) {
        try {
            var temp = mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusText").value("ok"))
                    .andExpect(jsonPath("$.statusCode").value("ok"))
                    .andExpect(jsonPath("$.data").doesNotExist());

        } catch (Exception e) {
            throw new RuntimeException("Failed to validate API", e);
        }
    }

    protected void validate404NotFound(MockHttpServletRequestBuilder request) {
        try {
            var temp = mockMvc.perform(request)
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.statusText").value("Not Found"))
                    .andExpect(jsonPath("$.statusCode").value("not.found"))
                    .andExpect(jsonPath("$.data").doesNotExist());

        } catch (Exception e) {
            throw new RuntimeException("Failed to validate API", e);
        }
    }

    protected void validateBadRequest(MockHttpServletRequestBuilder request) {
        try {
            var temp = mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data").doesNotExist());

        } catch (Exception e) {
            throw new RuntimeException("Failed to validate API", e);
        }
    }

    protected Technology technologyFixture(UUID techId) {
        return new Technology(techId,
                TECHNOLOGY_NAME,
                TECHNOLOGY_RING,
                TECHNOLOGY_QUADRANT,
                true,
                TECHNOLOGY_DESCRIPTION,
                JAVA_STUDIO.name(),
                QUARTER_1.name(),
                true,
                YEAR,
                Timestamp.from(ZonedDateTime.now().toInstant()),
                Timestamp.from(ZonedDateTime.now().toInstant()),
                null,
                null);
    }

    protected Technology technologyFixture(UUID techId, String technologyName, String technologyRing,
            String technologyDescription, String technologyStudio, QuarterType quarter) {
        return new Technology(techId,
                technologyName,
                technologyRing,
                TECHNOLOGY_QUADRANT,
                true,
                technologyDescription,
                technologyStudio,
                quarter.name(),
                true, YEAR,
                Timestamp.from(ZonedDateTime.now().toInstant()),
                Timestamp.from(ZonedDateTime.now().toInstant()),
                null,
                null);
    }
}
