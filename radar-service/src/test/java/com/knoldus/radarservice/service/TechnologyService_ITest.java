package com.knoldus.radarservice.service;

import com.knoldus.radarservice.config.IntegrationTestConfig;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.model.TechnologyRequest;
import com.knoldus.radarservice.model.TechnologyResponse;
import com.knoldus.radarservice.model.UpdateTechnologyRequest;
import com.knoldus.radarservice.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.knoldus.radarservice.model.QuarterType.QUARTER_1;
import static com.knoldus.radarservice.model.StudioType.JAVA_STUDIO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(
        scripts = "/setupDB.sql",
        config = @SqlConfig(transactionMode = TransactionMode.ISOLATED),
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
@ContextConfiguration(classes = {IntegrationTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TechnologyService_ITest {

    @Autowired
    private TechnologyService subject;

    @Autowired
    private TechnologyRepository repo;

    private static final String TECHNOLOGY_NAME = "java";
    private static final String TECHNOLOGY_RING = "assess";
    private static final String TECHNOLOGY_QUADRANT = "Languages and Frameworks";
    private static final String TECHNOLOGY_DESCRIPTION = "Java is a plausible choice for new products (due to its rich ecosystem)";
    private static final String YEAR = "2022";
    private static final TechnologyRequest TECHNOLOGY_REQUEST = new TechnologyRequest(TECHNOLOGY_NAME,
            TECHNOLOGY_RING,
            TECHNOLOGY_QUADRANT,
            true,
            TECHNOLOGY_DESCRIPTION,
            JAVA_STUDIO,
            QUARTER_1,
            true,
            YEAR,
            null,
            null);


    @Test
    void createTechnology() {
        // Given
        var technology = subject.createTechnology(TECHNOLOGY_REQUEST);

        assertEquals(TECHNOLOGY_NAME, technology.getName());
        assertEquals(TECHNOLOGY_RING, technology.getRing());
        assertEquals(TECHNOLOGY_QUADRANT, technology.getQuadrant());
        assertEquals(TECHNOLOGY_DESCRIPTION, technology.getDescription());
        assertEquals(JAVA_STUDIO.name(), technology.getStudio());
        assertEquals(QUARTER_1.name(), technology.getQuarter());
        assertTrue(technology.getActive());
        assertTrue(technology.getIsNew());
    }

    @Test
    void updateTechnology() {
        // Given
        UpdateTechnologyRequest updateTechnologyRequest = new UpdateTechnologyRequest();
        updateTechnologyRequest.setId(UUID.fromString("bd458343-3108-48ac-b8c0-7334e78e55fc"));
        updateTechnologyRequest.setName(TECHNOLOGY_NAME);

        var technology = subject.update(updateTechnologyRequest);

        assertEquals(TECHNOLOGY_NAME, technology.getName());
    }

    @Test
    void getTechnology_returnTechnology_whenQuarterAndStudioNotNull() {
        // given
        List<TechnologyResponse> technologies = subject.getTechnologyForRadar(QUARTER_1.name(), JAVA_STUDIO.name());

        assertThat(technologies).isNotNull();
    }

    @Test
    void getTechnology_returnTechnology_whenQuarterAndStudioNull() {
        // given
        List<TechnologyResponse> technologies = subject.getTechnologyForRadar(null, null);

        assertThat(technologies).isNotNull();
    }

    @Test
    void getTechnology_returnTechnology_whenQuarterIsNotNullAndStudioIsNull() {
        // given
        List<TechnologyResponse> technologies = subject.getTechnologyForRadar(QUARTER_1.name(), null);

        assertThat(technologies).isNotNull();
    }

    @Test
    void getTechnology_returnTechnology_whenQuarterIsNullAndStudioIsNotNull() {
        // given
        List<TechnologyResponse> technologies = subject.getTechnologyForRadar(null, JAVA_STUDIO.name());
        assertThat(technologies).isNotNull();
    }

    @Test
    void deleteTechnology() {
        var technologyId = "bd458343-3108-48ac-b8c0-7334e78e55fc";
        subject.delete(UUID.fromString(technologyId));

        var findTechnologyWithId = repo.findById(UUID.fromString(technologyId));

        assertTrue(findTechnologyWithId.isEmpty());
    }

    @Test
    void getTechnologyQuarterly() {
        // given
        List<Technology> technologies = subject.getTechnology();

        assertThat(technologies).isNotNull();
    }

    private Technology technologyFixture() {
        return new Technology(UUID.randomUUID(),
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

}
