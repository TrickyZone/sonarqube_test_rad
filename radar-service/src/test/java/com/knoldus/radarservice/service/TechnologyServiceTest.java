package com.knoldus.radarservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knoldus.radarservice.exception.TechnologyAlreadyExistsException;
import com.knoldus.radarservice.exception.TechnologyNotFoundException;
import com.knoldus.radarservice.model.QuarterResponse;
import com.knoldus.radarservice.model.QuarterType;
import com.knoldus.radarservice.model.StudioResponse;
import com.knoldus.radarservice.model.StudioType;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.model.TechnologyRequest;
import com.knoldus.radarservice.model.UpdateTechnologyRequest;
import com.knoldus.radarservice.repository.TechnologyRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TechnologyService.class})
@ExtendWith(SpringExtension.class)
class TechnologyServiceTest {
    @MockBean
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyService technologyService;

    /**
     * Method under test: {@link TechnologyService#createTechnology(TechnologyRequest)}
     */
    @Test
    void testCreateTechnology() {
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
        Optional<Technology> ofResult = Optional.of(technology);
        when(technologyRepository.findByName((String) any())).thenReturn(ofResult);
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.createTechnology(new TechnologyRequest()));
        verify(technologyRepository).findByName((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#createTechnology(TechnologyRequest)}
     */
    @Test
    void testCreateTechnology4() {
        when(technologyRepository.findByName((String) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.createTechnology(new TechnologyRequest()));
        verify(technologyRepository).findByName((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#createTechnology(TechnologyRequest)}
     */
    @Test
    void testCreateTechnology6() {
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
        when(technologyRepository.save((Technology) any())).thenReturn(technology);
        when(technologyRepository.findByName((String) any())).thenReturn(Optional.empty());

        TechnologyRequest technologyRequest = new TechnologyRequest("Name", "Ring", "Quadrant", true,
                "The characteristics of someone or something", StudioType.JAVA_STUDIO, QuarterType.QUARTER_1, true, "Year",
                "Alternate Technology", "https://example.org/example");
        technologyRequest.setIsNew(false);
        Technology actualCreateTechnologyResult = technologyService.createTechnology(technologyRequest);
        assertTrue(actualCreateTechnologyResult.getActive());
        assertEquals("Year", actualCreateTechnologyResult.getYear());
        assertEquals("JAVA_STUDIO", actualCreateTechnologyResult.getStudio());
        assertEquals("Ring", actualCreateTechnologyResult.getRing());
        assertEquals("QUARTER_1", actualCreateTechnologyResult.getQuarter());
        assertEquals("Quadrant", actualCreateTechnologyResult.getQuadrant());
        assertEquals("Name", actualCreateTechnologyResult.getName());
        assertFalse(actualCreateTechnologyResult.getIsNew());
        assertEquals("https://example.org/example", actualCreateTechnologyResult.getGithubUrl());
        assertEquals("Alternate Technology", actualCreateTechnologyResult.getAlternateTechnology());
        assertEquals("The characteristics of someone or something", actualCreateTechnologyResult.getDescription());
        verify(technologyRepository).save((Technology) any());
        verify(technologyRepository).findByName((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar() {
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("Quarter", "Studio").isEmpty());
        verify(technologyRepository).findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(),
                (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar2() {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("null");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("null");
        technology.setQuadrant("null");
        technology.setQuarter("null");
        technology.setRing("null");
        technology.setStudio("null");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("null");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology);
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(technologyList);
        assertEquals(1, technologyService.getTechnologyForRadar("Quarter", "Studio").size());
        verify(technologyRepository).findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(),
                (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar3() {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("null");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("null");
        technology.setQuadrant("null");
        technology.setQuarter("null");
        technology.setRing("null");
        technology.setStudio("null");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("null");

        Technology technology1 = new Technology();
        technology1.setActive(false);
        technology1.setAlternateTechnology("Quarter");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("null");
        technology1.setGithubUrl("null");
        technology1.setId(UUID.randomUUID());
        technology1.setIsNew(false);
        technology1.setName("Quarter");
        technology1.setQuadrant("Quarter");
        technology1.setQuarter("Quarter");
        technology1.setRing("Quarter");
        technology1.setStudio("Quarter");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setYear("Quarter");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology1);
        technologyList.add(technology);
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(technologyList);
        assertEquals(2, technologyService.getTechnologyForRadar("Quarter", "Studio").size());
        verify(technologyRepository).findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(),
                (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar4() {
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("null", "Studio").isEmpty());
        verify(technologyRepository).findByStudio((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar5() {
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("CURRENT_QUARTER", "Studio").isEmpty());
        verify(technologyRepository).findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(),
                (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar6() {
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar(null, "Studio").isEmpty());
        verify(technologyRepository).findByStudio((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar7() {
        when(technologyRepository.findAllByOrderByQuadrantAscRingAsc()).thenReturn(new ArrayList<>());
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("null", "null").isEmpty());
        verify(technologyRepository).findAllByOrderByQuadrantAscRingAsc();
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar8() {
        when(technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(technologyRepository.findAllByOrderByQuadrantAscRingAsc()).thenReturn(new ArrayList<>());
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("ALL_STUDIO", "null").isEmpty());
        verify(technologyRepository).findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar9() {
        when(technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(technologyRepository.findAllByOrderByQuadrantAscRingAsc()).thenReturn(new ArrayList<>());
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("CURRENT_QUARTER", "null").isEmpty());
        verify(technologyRepository).findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar10() {
        when(technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(technologyRepository.findAllByOrderByQuadrantAscRingAsc()).thenReturn(new ArrayList<>());
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.getTechnologyForRadar("ALL_STUDIO", null).isEmpty());
        verify(technologyRepository).findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar11() {
        when(technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        when(technologyRepository.findAllByOrderByQuadrantAscRingAsc()).thenReturn(new ArrayList<>());
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.getTechnologyForRadar("ALL_STUDIO", "null"));
        verify(technologyRepository).findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnologyForRadar(String, String)}
     */
    @Test
    void testGetTechnologyForRadar12() {
        when(technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        when(technologyRepository.findAllByOrderByQuadrantAscRingAsc()).thenReturn(new ArrayList<>());
        when(technologyRepository.findByStudio((String) any())).thenReturn(new ArrayList<>());
        when(technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any(),
                (String) any())).thenReturn(new ArrayList<>());
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.getTechnologyForRadar("CURRENT_QUARTER", "null"));
        verify(technologyRepository).findByQuarterAndYearOrderByQuadrantAscRingAsc((String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#update(UpdateTechnologyRequest)}
     */
    @Test
    void testUpdate() {
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
        Optional<Technology> ofResult = Optional.of(technology);

        Technology technology1 = new Technology();
        technology1.setActive(true);
        technology1.setAlternateTechnology("Alternate Technology");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("The characteristics of someone or something");
        technology1.setGithubUrl("https://example.org/example");
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
        when(technologyRepository.save((Technology) any())).thenReturn(technology1);
        when(technologyRepository.findById((UUID) any())).thenReturn(ofResult);
        assertSame(technology1, technologyService.update(new UpdateTechnologyRequest()));
        verify(technologyRepository).save((Technology) any());
        verify(technologyRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#update(UpdateTechnologyRequest)}
     */
    @Test
    void testUpdate2() {
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
        Optional<Technology> ofResult = Optional.of(technology);
        when(technologyRepository.save((Technology) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        when(technologyRepository.findById((UUID) any())).thenReturn(ofResult);
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.update(new UpdateTechnologyRequest()));
        verify(technologyRepository).save((Technology) any());
        verify(technologyRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#update(UpdateTechnologyRequest)}
     */
    @Test
    void testUpdate3() {
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
        when(technologyRepository.save((Technology) any())).thenReturn(technology);
        when(technologyRepository.findById((UUID) any())).thenReturn(Optional.empty());
        assertThrows(TechnologyNotFoundException.class, () -> technologyService.update(new UpdateTechnologyRequest()));
        verify(technologyRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#update(UpdateTechnologyRequest)}
     */
    @Test
    void testUpdate4() {
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
        Optional<Technology> ofResult = Optional.of(technology);

        Technology technology1 = new Technology();
        technology1.setActive(true);
        technology1.setAlternateTechnology("Alternate Technology");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("The characteristics of someone or something");
        technology1.setGithubUrl("https://example.org/example");
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

        Technology technology2 = new Technology();
        technology2.setActive(true);
        technology2.setAlternateTechnology("Alternate Technology");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology2.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        technology2.setDescription("The characteristics of someone or something");
        technology2.setGithubUrl("https://example.org/example");
        technology2.setId(UUID.randomUUID());
        technology2.setIsNew(true);
        technology2.setName("Name");
        technology2.setQuadrant("Quadrant");
        technology2.setQuarter("Quarter");
        technology2.setRing("Ring");
        technology2.setStudio("Studio");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology2.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        technology2.setYear("Year");
        Optional<Technology> ofResult1 = Optional.of(technology2);
        when(technologyRepository.findByNameAndIdNot((String) any(), (UUID) any())).thenReturn(ofResult1);
        when(technologyRepository.save((Technology) any())).thenReturn(technology1);
        when(technologyRepository.findById((UUID) any())).thenReturn(ofResult);
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.update(new UpdateTechnologyRequest(UUID.randomUUID(), "Name", "Ring", "Quadrant",
                        true, "The characteristics of someone or something", StudioType.JAVA_STUDIO, QuarterType.QUARTER_1, true,
                        "Year")));
        verify(technologyRepository).findByNameAndIdNot((String) any(), (UUID) any());
        verify(technologyRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#delete(UUID)}
     */
    @Test
    void testDelete() {
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
        Optional<Technology> ofResult = Optional.of(technology);
        doNothing().when(technologyRepository).deleteById((UUID) any());
        when(technologyRepository.findById((UUID) any())).thenReturn(ofResult);
        technologyService.delete(UUID.randomUUID());
        verify(technologyRepository).findById((UUID) any());
        verify(technologyRepository).deleteById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#delete(UUID)}
     */
    @Test
    void testDelete2() {
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
        Optional<Technology> ofResult = Optional.of(technology);
        doThrow(new TechnologyAlreadyExistsException("An error occurred")).when(technologyRepository)
                .deleteById((UUID) any());
        when(technologyRepository.findById((UUID) any())).thenReturn(ofResult);
        assertThrows(TechnologyAlreadyExistsException.class, () -> technologyService.delete(UUID.randomUUID()));
        verify(technologyRepository).findById((UUID) any());
        verify(technologyRepository).deleteById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#delete(UUID)}
     */
    @Test
    void testDelete3() {
        doNothing().when(technologyRepository).deleteById((UUID) any());
        when(technologyRepository.findById((UUID) any())).thenReturn(Optional.empty());
        assertThrows(TechnologyNotFoundException.class, () -> technologyService.delete(UUID.randomUUID()));
        verify(technologyRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link TechnologyService#getTechnology()}
     */
    @Test
    void testGetTechnology() {
        ArrayList<Technology> technologyList = new ArrayList<>();
        when(technologyRepository.findAllByOrderByCreatedAtDesc()).thenReturn(technologyList);
        List<Technology> actualTechnology = technologyService.getTechnology();
        assertSame(technologyList, actualTechnology);
        assertTrue(actualTechnology.isEmpty());
        verify(technologyRepository).findAllByOrderByCreatedAtDesc();
    }

    /**
     * Method under test: {@link TechnologyService#getTechnology()}
     */
    @Test
    void testGetTechnology2() {
        when(technologyRepository.findAllByOrderByCreatedAtDesc())
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class, () -> technologyService.getTechnology());
        verify(technologyRepository).findAllByOrderByCreatedAtDesc();
    }

    /**
     * Method under test: {@link TechnologyService#getAllStudio()}
     */
    @Test
    void testGetAllStudio() {
        when(technologyRepository.findAll()).thenReturn(new ArrayList<>());
        List<StudioResponse> actualAllStudio = technologyService.getAllStudio();
        assertEquals(1, actualAllStudio.size());
        StudioResponse getResult = actualAllStudio.get(0);
        assertEquals("ALL_STUDIO", getResult.getStudioId());
        assertEquals("All Studio", getResult.getStudioName());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#getAllStudio()}
     */
    @Test
    void testGetAllStudio2() {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("ALL_STUDIO");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("ALL_STUDIO");
        technology.setQuadrant("ALL_STUDIO");
        technology.setQuarter("ALL_STUDIO");
        technology.setRing("ALL_STUDIO");
        technology.setStudio("ALL_STUDIO");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("ALL_STUDIO");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology);
        when(technologyRepository.findAll()).thenReturn(technologyList);
        List<StudioResponse> actualAllStudio = technologyService.getAllStudio();
        assertEquals(2, actualAllStudio.size());
        StudioResponse getResult = actualAllStudio.get(0);
        assertEquals("All Studio", getResult.getStudioName());
        StudioResponse getResult1 = actualAllStudio.get(1);
        assertEquals("ALL STUDIO", getResult1.getStudioName());
        assertEquals("ALL_STUDIO", getResult1.getStudioId());
        assertEquals("ALL_STUDIO", getResult.getStudioId());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#getAllStudio()}
     */
    @Test
    void testGetAllStudio3() {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("ALL_STUDIO");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("ALL_STUDIO");
        technology.setQuadrant("ALL_STUDIO");
        technology.setQuarter("ALL_STUDIO");
        technology.setRing("ALL_STUDIO");
        technology.setStudio("ALL_STUDIO");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("ALL_STUDIO");

        Technology technology1 = new Technology();
        technology1.setActive(false);
        technology1.setAlternateTechnology("All Studio");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("ALL_STUDIO");
        technology1.setGithubUrl("ALL_STUDIO");
        technology1.setId(UUID.randomUUID());
        technology1.setIsNew(false);
        technology1.setName("All Studio");
        technology1.setQuadrant("All Studio");
        technology1.setQuarter("All Studio");
        technology1.setRing("All Studio");
        technology1.setStudio("All Studio");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setYear("All Studio");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology1);
        technologyList.add(technology);
        when(technologyRepository.findAll()).thenReturn(technologyList);
        List<StudioResponse> actualAllStudio = technologyService.getAllStudio();
        assertEquals(3, actualAllStudio.size());
        StudioResponse getResult = actualAllStudio.get(2);
        assertEquals("All Studio", getResult.getStudioName());
        StudioResponse getResult1 = actualAllStudio.get(1);
        assertEquals("ALL STUDIO", getResult1.getStudioName());
        assertEquals("ALL_STUDIO", getResult1.getStudioId());
        assertEquals("All Studio", getResult.getStudioId());
        StudioResponse getResult2 = actualAllStudio.get(0);
        assertEquals("ALL_STUDIO", getResult2.getStudioId());
        assertEquals("All Studio", getResult2.getStudioName());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#getAllStudio()}
     */
    @Test
    void testGetAllStudio4() {
        when(technologyRepository.findAll()).thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class, () -> technologyService.getAllStudio());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfAreaAndQuadrantAndStudio() {
        when(technologyRepository.findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(technologyService.fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio("Area", "Quarter", "Studio")
                .isEmpty());
        verify(technologyRepository).findAllByAreaAndStudioAndQuadrant((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String, String, String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfAreaAndQuadrantAndStudio2() {
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

        Technology technology1 = new Technology();
        technology1.setActive(false);
        technology1.setAlternateTechnology("com.knoldus.radarservice.model.Technology");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("Description");
        technology1.setGithubUrl("Github Url");
        technology1.setId(UUID.randomUUID());
        technology1.setIsNew(false);
        technology1.setName("com.knoldus.radarservice.model.Technology");
        technology1.setQuadrant("com.knoldus.radarservice.model.Technology");
        technology1.setQuarter("com.knoldus.radarservice.model.Technology");
        technology1.setRing("com.knoldus.radarservice.model.Technology");
        technology1.setStudio("com.knoldus.radarservice.model.Technology");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setYear("com.knoldus.radarservice.model.Technology");

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
    void testFetchTechnologyWithRespectOfName() {
        when(technologyRepository.findByNameContainingIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(technologyService.fetchTechnologyWithRespectOfName("Name").isEmpty());
        verify(technologyRepository).findByNameContainingIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfName(String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfName2() {
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

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology);
        when(technologyRepository.findByNameContainingIgnoreCase((String) any())).thenReturn(technologyList);
        assertEquals(1, technologyService.fetchTechnologyWithRespectOfName("Name").size());
        verify(technologyRepository).findByNameContainingIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfName(String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfName3() {
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

        Technology technology1 = new Technology();
        technology1.setActive(false);
        technology1.setAlternateTechnology("com.knoldus.radarservice.model.Technology");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("Description");
        technology1.setGithubUrl("Github Url");
        technology1.setId(UUID.randomUUID());
        technology1.setIsNew(false);
        technology1.setName("com.knoldus.radarservice.model.Technology");
        technology1.setQuadrant("com.knoldus.radarservice.model.Technology");
        technology1.setQuarter("com.knoldus.radarservice.model.Technology");
        technology1.setRing("com.knoldus.radarservice.model.Technology");
        technology1.setStudio("com.knoldus.radarservice.model.Technology");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setYear("com.knoldus.radarservice.model.Technology");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology1);
        technologyList.add(technology);
        when(technologyRepository.findByNameContainingIgnoreCase((String) any())).thenReturn(technologyList);
        assertEquals(2, technologyService.fetchTechnologyWithRespectOfName("Name").size());
        verify(technologyRepository).findByNameContainingIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#fetchTechnologyWithRespectOfName(String)}
     */
    @Test
    void testFetchTechnologyWithRespectOfName4() {
        when(technologyRepository.findByNameContainingIgnoreCase((String) any()))
                .thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class,
                () -> technologyService.fetchTechnologyWithRespectOfName("Name"));
        verify(technologyRepository).findByNameContainingIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link TechnologyService#getAllQuarter()}
     */
    @Test
    void testGetAllQuarter() {
        when(technologyRepository.findAll()).thenReturn(new ArrayList<>());
        List<QuarterResponse> actualAllQuarter = technologyService.getAllQuarter();
        assertEquals(2, actualAllQuarter.size());
        QuarterResponse getResult = actualAllQuarter.get(0);
        assertEquals("All Time", getResult.getQuarterName());
        QuarterResponse getResult1 = actualAllQuarter.get(1);
        assertEquals("Current Quarter", getResult1.getQuarterName());
        assertEquals("CURRENT_QUARTER", getResult1.getQuarterId());
        assertEquals("ALL_QUARTER", getResult.getQuarterId());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#getAllQuarter()}
     */
    @Test
    void testGetAllQuarter2() {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("ALL_QUARTER");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("ALL_QUARTER");
        technology.setQuadrant("ALL_QUARTER");
        technology.setQuarter("ALL_QUARTER");
        technology.setRing("ALL_QUARTER");
        technology.setStudio("ALL_QUARTER");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("ALL_QUARTER");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology);
        when(technologyRepository.findAll()).thenReturn(technologyList);
        List<QuarterResponse> actualAllQuarter = technologyService.getAllQuarter();
        assertEquals(3, actualAllQuarter.size());
        QuarterResponse getResult = actualAllQuarter.get(2);
        assertEquals("ALL QUARTER", getResult.getQuarterName());
        QuarterResponse getResult1 = actualAllQuarter.get(1);
        assertEquals("Current Quarter", getResult1.getQuarterName());
        assertEquals("CURRENT_QUARTER", getResult1.getQuarterId());
        assertEquals("ALL_QUARTER", getResult.getQuarterId());
        QuarterResponse getResult2 = actualAllQuarter.get(0);
        assertEquals("ALL_QUARTER", getResult2.getQuarterId());
        assertEquals("All Time", getResult2.getQuarterName());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#getAllQuarter()}
     */
    @Test
    void testGetAllQuarter3() {
        Technology technology = new Technology();
        technology.setActive(true);
        technology.setAlternateTechnology("ALL_QUARTER");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("The characteristics of someone or something");
        technology.setGithubUrl("https://example.org/example");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(true);
        technology.setName("ALL_QUARTER");
        technology.setQuadrant("ALL_QUARTER");
        technology.setQuarter("ALL_QUARTER");
        technology.setRing("ALL_QUARTER");
        technology.setStudio("ALL_QUARTER");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("ALL_QUARTER");

        Technology technology1 = new Technology();
        technology1.setActive(false);
        technology1.setAlternateTechnology("All Time");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("ALL_QUARTER");
        technology1.setGithubUrl("ALL_QUARTER");
        technology1.setId(UUID.randomUUID());
        technology1.setIsNew(false);
        technology1.setName("All Time");
        technology1.setQuadrant("All Time");
        technology1.setQuarter("All Time");
        technology1.setRing("All Time");
        technology1.setStudio("All Time");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setYear("All Time");

        ArrayList<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology1);
        technologyList.add(technology);
        when(technologyRepository.findAll()).thenReturn(technologyList);
        List<QuarterResponse> actualAllQuarter = technologyService.getAllQuarter();
        assertEquals(4, actualAllQuarter.size());
        QuarterResponse getResult = actualAllQuarter.get(1);
        assertEquals("Current Quarter", getResult.getQuarterName());
        QuarterResponse getResult1 = actualAllQuarter.get(2);
        assertEquals("ALL QUARTER", getResult1.getQuarterName());
        assertEquals("ALL_QUARTER", getResult1.getQuarterId());
        assertEquals("CURRENT_QUARTER", getResult.getQuarterId());
        QuarterResponse getResult2 = actualAllQuarter.get(3);
        assertEquals("All Time", getResult2.getQuarterId());
        QuarterResponse getResult3 = actualAllQuarter.get(0);
        assertEquals("ALL_QUARTER", getResult3.getQuarterId());
        assertEquals("All Time", getResult2.getQuarterName());
        assertEquals("All Time", getResult3.getQuarterName());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#getAllQuarter()}
     */
    @Test
    void testGetAllQuarter4() {
        when(technologyRepository.findAll()).thenThrow(new TechnologyAlreadyExistsException("An error occurred"));
        assertThrows(TechnologyAlreadyExistsException.class, () -> technologyService.getAllQuarter());
        verify(technologyRepository).findAll();
    }

    /**
     * Method under test: {@link TechnologyService#createTechnologies(List)}
     */
    @Test
    void testCreateTechnologies() {
        ArrayList<Technology> technologyList = new ArrayList<>();
        when(technologyRepository.findByNameIgnoreCaseIn((Set<String>) any())).thenReturn(technologyList);
        when(technologyRepository.saveAll((Iterable<Technology>) any())).thenReturn(new ArrayList<>());
        Collection<Technology> actualCreateTechnologiesResult = technologyService.createTechnologies(new ArrayList<>());
        assertSame(technologyList, actualCreateTechnologiesResult);
        assertTrue(actualCreateTechnologiesResult.isEmpty());
        verify(technologyRepository).findByNameIgnoreCaseIn((Set<String>) any());
        verify(technologyRepository).saveAll((Iterable<Technology>) any());
    }

    /**
     * Method under test: {@link TechnologyService#createTechnologies(List)}
     */
    @Test
    void testCreateTechnologies3() {
        when(technologyRepository.findByNameIgnoreCaseIn((Set<String>) any()))
                .thenThrow(new TechnologyNotFoundException("An error occurred"));
        when(technologyRepository.saveAll((Iterable<Technology>) any()))
                .thenThrow(new TechnologyNotFoundException("An error occurred"));
        assertThrows(TechnologyNotFoundException.class, () -> technologyService.createTechnologies(new ArrayList<>()));
        verify(technologyRepository).findByNameIgnoreCaseIn((Set<String>) any());
    }

    /**
     * Method under test: {@link TechnologyService#createTechnologies(List)}
     */
    @Test
    void testCreateTechnologies7() {
        ArrayList<Technology> technologyList = new ArrayList<>();
        when(technologyRepository.findByNameIgnoreCaseIn((Set<String>) any())).thenReturn(technologyList);
        when(technologyRepository.saveAll((Iterable<Technology>) any())).thenReturn(new ArrayList<>());

        TechnologyRequest technologyRequest = new TechnologyRequest("Name", "Ring", "Quadrant", true,
                "The characteristics of someone or something", StudioType.JAVA_STUDIO, QuarterType.QUARTER_1, true, "Year",
                "Alternate Technology", "https://example.org/example");
        technologyRequest.setStudio(StudioType.SCALA_STUDIO);

        ArrayList<TechnologyRequest> technologyRequestList = new ArrayList<>();
        technologyRequestList.add(technologyRequest);
        Collection<Technology> actualCreateTechnologiesResult = technologyService
                .createTechnologies(technologyRequestList);
        assertSame(technologyList, actualCreateTechnologiesResult);
        assertTrue(actualCreateTechnologiesResult.isEmpty());
        verify(technologyRepository).findByNameIgnoreCaseIn((Set<String>) any());
        verify(technologyRepository).saveAll((Iterable<Technology>) any());
    }

    /**
     * Method under test: {@link TechnologyService#createTechnologies(List)}
     */
    @Test
    void testCreateTechnologies8() {
        Technology technology = new Technology();
        technology.setActive(false);
        technology.setAlternateTechnology("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setDescription("com.knoldus.radarservice.model.Technology");
        technology.setGithubUrl("com.knoldus.radarservice.model.Technology");
        technology.setId(UUID.randomUUID());
        technology.setIsNew(false);
        technology.setName("42");
        technology.setQuadrant("42");
        technology.setQuarter("42");
        technology.setRing("42");
        technology.setStudio("42");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        technology.setYear("42");

        Technology technology1 = new Technology();
        technology1.setActive(true);
        technology1.setAlternateTechnology("Alternate Technology");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        technology1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        technology1.setDescription("The characteristics of someone or something");
        technology1.setGithubUrl("https://example.org/example");
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
        when(technologyRepository.findByNameIgnoreCaseIn((Set<String>) any())).thenReturn(technologyList);
        when(technologyRepository.saveAll((Iterable<Technology>) any())).thenReturn(new ArrayList<>());

        TechnologyRequest technologyRequest = new TechnologyRequest("Name", "Ring", "Quadrant", true,
                "The characteristics of someone or something", StudioType.JAVA_STUDIO, QuarterType.QUARTER_1, true, "Year",
                "Alternate Technology", "https://example.org/example");
        technologyRequest.setStudio(StudioType.SCALA_STUDIO);

        ArrayList<TechnologyRequest> technologyRequestList = new ArrayList<>();
        technologyRequestList.add(technologyRequest);
        Collection<Technology> actualCreateTechnologiesResult = technologyService
                .createTechnologies(technologyRequestList);
        assertSame(technologyList, actualCreateTechnologiesResult);
        assertEquals(2, actualCreateTechnologiesResult.size());
        verify(technologyRepository).findByNameIgnoreCaseIn((Set<String>) any());
        verify(technologyRepository).saveAll((Iterable<Technology>) any());
    }
}

