package com.knoldus.radarservice.service;

import com.knoldus.radarservice.base.CommonTestUtils;
import com.knoldus.radarservice.config.IntegrationTestConfig;
import com.knoldus.radarservice.exception.CSVUploadException;
import com.knoldus.radarservice.repository.TechnologyRepository;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = {IntegrationTestConfig.class})
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CSVService_ITest implements CommonTestUtils {

    @Autowired
    private CSVService subject;

    @Autowired
    private TechnologyRepository repo;

    @BeforeEach
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    void createTechnology() throws IOException {
        String path = "dummydata/technologies.csv";
        MultipartFile multipartFile = new MockMultipartFile("file", "technologies.csv", "text/csv",readFileFromResource(path));

        subject.save(multipartFile);

        var actualOutputCount = repo.findAll().size();

        assertEquals(2, actualOutputCount);
    }

    @Test
    void createTechnology_HandleException() throws IOException {

        String path = "dummydata/invalid_technologies.csv";
        MultipartFile multipartFile = new MockMultipartFile("file", "technologies.csv", "text/csv",readFileFromResource(path));

        assertThrows(CSVUploadException.class, () -> subject.save(multipartFile));
    }
}
