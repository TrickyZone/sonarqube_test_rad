package com.knoldus.radarservice.service;

import com.knoldus.radarservice.exception.TechnologyAlreadyExistsException;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.exception.TechnologyNotFoundException;
import com.knoldus.radarservice.model.TechnologyRequest;
import com.knoldus.radarservice.model.UpdateTechnologyRequest;
import com.knoldus.radarservice.model.*;
import com.knoldus.radarservice.repository.TechnologyRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.knoldus.radarservice.model.ErrorType.REJECTED_TECHNOLOGY_ALREADY_EXISTS;
import static com.knoldus.radarservice.model.QuarterType.QUARTER_1;
import static com.knoldus.radarservice.model.QuarterType.QUARTER_2;
import static com.knoldus.radarservice.model.QuarterType.QUARTER_3;
import static com.knoldus.radarservice.model.QuarterType.QUARTER_4;


@Service
@AllArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    private static final String ALL_QUARTER = "ALL_QUARTER";
    private static final String ALL_QUARTER_VALUE = "All Time";
    private static final String CURRENT_QUARTER = "CURRENT_QUARTER";
    private static final String CURRENT_QUARTER_VALUE = "Current Quarter";
    private static final String ALL_STUDIO = "ALL_STUDIO";
    private static final String ALL_STUDIO_VALUE = "All Studio";
    private static final String YEAR_2021 = "2021";
    private static final String CURRENT_YEAR = "CURRENT_YEAR";

    /**
     * Registers new technology into the radar.
     *
     * @param technologyRequest contains the data related to technology
     * @return the technology
     */
    public Technology createTechnology(TechnologyRequest technologyRequest) {
        String name = technologyRequest.getName();

        Optional<Technology> optionalTechnology = technologyRepository.findByName(name);
        if (optionalTechnology.isPresent()) {
            throw new TechnologyAlreadyExistsException(REJECTED_TECHNOLOGY_ALREADY_EXISTS.toString());
        }

        String ring = technologyRequest.getRing();
        String quadrant = technologyRequest.getQuadrant();
        String description = technologyRequest.getDescription();
        boolean isNew = technologyRequest.getIsNew();
        String studio = technologyRequest.getStudio().name();
        String quarter = technologyRequest.getQuarter().name();
        boolean active = technologyRequest.getActive();
        String year = technologyRequest.getYear();
        UUID id = UUID.randomUUID();
        String alternateTechnology = technologyRequest.getAlternateTechnology();
        String githubUrl = technologyRequest.getGithubUrl();

        Technology technology = new Technology(id, name, ring, quadrant, isNew, description, studio, quarter, active,
                year, Timestamp.from(ZonedDateTime.now().toInstant()), Timestamp.from(ZonedDateTime.now().toInstant()),alternateTechnology,githubUrl);
        technologyRepository.save(technology);
        return technology;
    }

    /**
     * Retrieves/Fetches the existing technologies which are present in the knoldus radar.
     *
     * @return the list of existing technologies
     */
    public List<TechnologyResponse> getTechnologyForRadar(String quarter, String studio) {
        List<Technology> technologyList;

        quarter = quarter != null && !quarter.equals("null") ? quarter : ALL_QUARTER;
        studio = studio != null && !studio.equals("null") ? studio : ALL_STUDIO;

        if (quarter.equals(ALL_QUARTER) && studio.equals(ALL_STUDIO)) {
            technologyList = technologyRepository.findAllByOrderByQuadrantAscRingAsc();
        } else if (quarter.equals(CURRENT_QUARTER)) {
            Map<String, String> currentQuarterAndYearMap = getCurrentQuarterAndYear();
            String currentQuarter = currentQuarterAndYearMap.get(CURRENT_QUARTER);
            String currentYear = currentQuarterAndYearMap.get(CURRENT_YEAR);
            if (studio.equals(ALL_STUDIO)) {
                technologyList = technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc(currentQuarter,
                        currentYear);
            } else {
                technologyList = technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc(
                        currentQuarter, studio,
                        currentYear);
            }
        } else if (!studio.equals(ALL_STUDIO) && quarter.equals(ALL_QUARTER)) {
            technologyList = technologyRepository.findByStudio(studio);
        } else {
            if (studio.equals(ALL_STUDIO)) {
                technologyList = technologyRepository.findByQuarterAndYearOrderByQuadrantAscRingAsc(QUARTER_1.name(),
                        YEAR_2021);
            } else {
                technologyList = technologyRepository.findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc(quarter,
                        studio,
                        YEAR_2021);
            }
        }

        List<TechnologyResponse> technologyResponseList = new ArrayList<>();
        technologyList.forEach(ref -> {
            TechnologyResponse response = new TechnologyResponse();
            response.setName(ref.getName());
            response.setDescription(ref.getDescription());
            response.setQuadrant(ref.getQuadrant());
            response.setRing(ref.getRing());
            response.setIsNew(String.valueOf(ref.getIsNew()));
            technologyResponseList.add(response);
        });
        return technologyResponseList;
    }

    public Technology update(UpdateTechnologyRequest request) {
        UUID technologyId = request.getId();

        var oldTechnologyOptional = technologyRepository.findById(technologyId);


        if (oldTechnologyOptional.isEmpty()) {
            throw new TechnologyNotFoundException();
        }
        Technology technology = oldTechnologyOptional.get();
        Optional<String> optionalName = Optional.ofNullable(request.getName());

        if (optionalName.isPresent()) {
            optionalName.ifPresent(technology::setName);
            String name = optionalName.get();
            Optional<Technology> optionalTechnology = technologyRepository.findByNameAndIdNot(name, technologyId);
            if (optionalTechnology.isPresent()) {
                throw new TechnologyAlreadyExistsException(REJECTED_TECHNOLOGY_ALREADY_EXISTS.toString());
            }

        }

        Optional<String> optionalRing = Optional.ofNullable(request.getRing());
        optionalRing.ifPresent(technology::setRing);

        Optional<String> optionalQuadrant = Optional.ofNullable(request.getQuadrant());
        optionalQuadrant.ifPresent(technology::setQuadrant);

        Optional<Boolean> optionalIsNew = Optional.ofNullable(request.getIsNew());
        optionalIsNew.ifPresent(technology::setIsNew);

        Optional<String> optionalDescription = Optional.ofNullable(request.getDescription());
        optionalDescription.ifPresent(technology::setDescription);

        Optional<StudioType> optionalStudio = Optional.ofNullable(request.getStudio());
        optionalStudio.ifPresent(studio -> technology.setStudio(studio.name()));

        Optional<QuarterType> optionalQuarter = Optional.ofNullable(request.getQuarter());
        optionalQuarter.ifPresent(quarters -> technology.setQuarter(quarters.name()));

        Optional<Boolean> optionalActive = Optional.ofNullable(request.getActive());
        optionalActive.ifPresent(technology::setActive);

        Optional<String> optionalYear = Optional.ofNullable(request.getYear());
        optionalYear.ifPresent(technology::setYear);
        technology.setUpdatedAt(Timestamp.from(ZonedDateTime.now().toInstant()));

        return technologyRepository.save(technology);
    }

    public void delete(UUID techId) {

        var isTechnologyExist = technologyRepository.findById(techId);

        if (isTechnologyExist.isEmpty()) {
            throw new TechnologyNotFoundException();
        }

        technologyRepository.deleteById(techId);
    }

    /**
     * Retrieves/Fetches the existing technologies.
     *
     * @return the list of existing technologies based on quarter
     */
    public List<Technology> getTechnology() {

        return technologyRepository.findAllByOrderByCreatedAtDesc();
    }


    /**
     * Retrieves/Fetches the existing studio.
     *
     * @return the list of existing studio
     */
    public List<StudioResponse> getAllStudio() {

        List<StudioResponse> studioResponseList = new ArrayList<>();
        studioResponseList.add(new StudioResponse(ALL_STUDIO, ALL_STUDIO_VALUE));
        var studioList = technologyRepository.findAll().stream().map(Technology::getStudio).distinct().sorted();

        studioList.forEach(studio -> {
            StudioResponse studioResponse = new StudioResponse();
            studioResponse.setStudioId(studio);
            studioResponse.setStudioName(studio.replace("_", " "));
            studioResponseList.add(studioResponse);
        });

        return studioResponseList;
    }

    public List<TechnologyResponse> fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(String area, String quarter, String studio) {
        List<Technology> technologyList;
        List<Technology> allByAreaAndStudioAndQuadrant = technologyRepository.findAllByAreaAndStudioAndQuadrant(area, studio, quarter);
        List<TechnologyResponse> technologyResponseList = new ArrayList<>();
        responseList(allByAreaAndStudioAndQuadrant,technologyResponseList);
        return technologyResponseList;
    }

    public List<TechnologyResponse> fetchTechnologyWithRespectOfName(String name) {
        List<Technology> technologyList;
        List<Technology> allByName= technologyRepository.findByNameContainingIgnoreCase(name);
        List<TechnologyResponse> technologyResponseList = new ArrayList<>();
        responseList(allByName,technologyResponseList);
        return technologyResponseList;
    }

    private void responseList(List<Technology> fetchAll, List<TechnologyResponse> technologyResponseList) {
        fetchAll.forEach(ref -> {
            TechnologyResponse response = new TechnologyResponse();
            response.setName(ref.getName());
            response.setDescription(ref.getDescription());
            response.setQuadrant(ref.getQuadrant());
            response.setRing(ref.getRing());
            response.setIsNew(String.valueOf(ref.getIsNew()));
            response.setStudio(ref.getStudio());
            response.setQuarter(ref.getQuarter());
            technologyResponseList.add(response);
        });
    }

    /**
     * Retrieves/Fetches the existing quarter.
     *
     * @return the list of existing quarter
     */
    public List<QuarterResponse> getAllQuarter() {

        List<QuarterResponse> quarterResponseList = new ArrayList<>();
        quarterResponseList.add(new QuarterResponse(ALL_QUARTER, ALL_QUARTER_VALUE));
        quarterResponseList.add(new QuarterResponse(CURRENT_QUARTER, CURRENT_QUARTER_VALUE));
        var studioList = technologyRepository.findAll().stream().map(Technology::getQuarter).distinct().sorted();

        studioList.forEach(quarter -> {
            QuarterResponse quarterResponse = new QuarterResponse();
            quarterResponse.setQuarterId(quarter);
            quarterResponse.setQuarterName(quarter.replace("_", " "));
            quarterResponseList.add(quarterResponse);
        });

        return quarterResponseList;
    }



    /**
     * Add multiple technologies
     *
     * @param technologiesRequest collection containing the all the technologies that needs to be added
     * @return the list of duplicate technology name
     */
    public Collection<Technology> createTechnologies(List<TechnologyRequest> technologiesRequest) {
        Set<String> technologySet = technologiesRequest.stream().map(TechnologyRequest::getName)
                .collect(Collectors.toSet());

        Collection<Technology> duplicateTechnologies = technologyRepository.findByNameIgnoreCaseIn(technologySet);

        Set<Technology> finalCollection = new HashSet<>();

        technologiesRequest.forEach(technology -> {
            var techs = duplicateTechnologies.stream()
                    .filter(x -> x.getName().equalsIgnoreCase(technology.getName())).collect(Collectors.toList());

            if (techs.isEmpty()) {
                finalCollection.add(new Technology(UUID.randomUUID(), technology.getName(), technology.getRing(),
                        technology.getQuadrant(), technology.getIsNew(), technology.getDescription(),
                        technology.getStudio().name(), technology.getQuarter().name(), technology.getActive(),
                        technology.getYear(), Timestamp.from(ZonedDateTime.now().toInstant()),
                        Timestamp.from(ZonedDateTime.now().toInstant()),technology.getAlternateTechnology(),technology.getGithubUrl()));
            }
        });

        technologyRepository.saveAll(finalCollection);

        return duplicateTechnologies;
    }


    private Map<String, String> getCurrentQuarterAndYear() {
        Map<String, String> currentQuarterAndYearMap = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        Integer currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        String currentQuarter;
        if (currentMonth <= 3) {
            currentQuarter = QUARTER_1.name();
        } else if (currentMonth <= 6) {
            currentQuarter = QUARTER_2.name();
        } else if (currentMonth <= 9) {
            currentQuarter = QUARTER_3.name();
        } else {
            currentQuarter = QUARTER_4.name();
        }
        currentQuarterAndYearMap.put(CURRENT_QUARTER, currentQuarter);
        currentQuarterAndYearMap.put(CURRENT_YEAR, String.valueOf(currentYear));
        return currentQuarterAndYearMap;
    }



}
