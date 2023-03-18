package com.knoldus.radarservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knoldus.radarservice.exception.CSVUploadException;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.repository.TechnologyRepository;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {CSVService.class})
@ExtendWith(SpringExtension.class)
class CSVServiceTest {
    @Autowired
    private CSVService cSVService;

    @MockBean
    private TechnologyRepository technologyRepository;

    /**
     * Method under test: {@link CSVService#save(MultipartFile)}
     */
    @Test
    void testSave() throws IOException {
        ArrayList<Technology> technologyList = new ArrayList<>();
        when(technologyRepository.findByNameIgnoreCaseIn((Set<String>) any())).thenReturn(technologyList);
        when(technologyRepository.saveAll((Iterable<Technology>) any())).thenReturn(new ArrayList<>());
        Collection<Technology> actualSaveResult = cSVService
                .save(new MockMultipartFile("Test Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
        assertSame(technologyList, actualSaveResult);
        assertTrue(actualSaveResult.isEmpty());
        verify(technologyRepository).findByNameIgnoreCaseIn((Set<String>) any());
        verify(technologyRepository).saveAll((Iterable<Technology>) any());
    }
}

