package com.knoldus.radarservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knoldus.radarservice.exception.TechnologyAlreadyExistsException;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.repository.TechnologyRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = {TechnologyService.class})
@SpringBootTest
class TechnologyServiceTest {
    @MockBean
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyService technologyService;

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfAreaAndQuadrantAndStudio() {
        when(technologyRepository.findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(
                technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio("Area", "Quarter", "Studio").isEmpty());
        verify(technologyRepository).findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfAreaAndQuadrantAndStudio2() {
        Technology technology = new Technology();
        technology.setActive(true);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
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

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology);
        when(technologyRepository.findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any()))
                .thenReturn(technologyList);
        assertEquals(1,
                technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio("Area", "Quarter", "Studio").size());
        verify(technologyRepository).findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfAreaAndQuadrantAndStudio3() {
        Technology technology = new Technology();
        technology.setActive(true);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
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

        Technology technology1 = new Technology();
        technology1.setActive(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("The characteristics of someone or something");
        technology1.setId(UUID.randomUUID());
        technology1.setIsNew(true);
        technology1.setName("Name");
        technology1.setQuadrant("Quadrant");
        technology1.setQuarter("Quarter");
        technology1.setRing("Ring");
        technology1.setStudio("Studio");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setYear("Year");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology1);
        technologyList.add(technology);
        when(technologyRepository.findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any()))
                .thenReturn(technologyList);
        assertEquals(2,
                technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio("Area", "Quarter", "Studio").size());
        verify(technologyRepository).findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfAreaAndQuadrantAndStudio4() {
        when(technologyRepository.findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio("Area", "Quarter", "Studio"));
        verify(technologyRepository).findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfName(String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfName1() {
        when(technologyRepository.findByNameContainingIgnoreCase((String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(
                technologyService.fetchTechnologyWithRespectOfName("name").isEmpty());
        verify(technologyRepository).findByNameContainingIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfName(String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfName2() {
        when(technologyRepository.findByNameContainingIgnoreCase((String) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.fetchTechnologyWithRespectOfName("name"));
        verify(technologyRepository).findByNameContainingIgnoreCase((String) any());
    }


}
