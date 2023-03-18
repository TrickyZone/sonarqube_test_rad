package com.knoldus.radarservice.service;

import com.knoldus.radarservice.exception.CSVUploadException;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.repository.TechnologyRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * This service uploads the csv file to the database after creating the collection of technologies.
 */
@Service
@AllArgsConstructor
public class CSVService {

    TechnologyRepository repository;

    /**
     * this method convert the csv file to the technologies and persist the same to DB
     *
     * @param file contains the representation of an uploaded file received in a multipart request.
     */
    public Collection<Technology> save(MultipartFile file) {
        Collection<Technology> duplicateTechnologies;
        try {
            List<Technology> technologies = CSVHelper.csvToTechnologies(file.getInputStream());
            Set<String> technologySet = technologies.stream().map(Technology::getName).collect(Collectors.toSet());

            duplicateTechnologies = repository.findByNameIgnoreCaseIn(technologySet);

            Set<Technology> finalCollection = new HashSet<>();

            technologies.forEach(technology -> {
                var techs = duplicateTechnologies.stream()
                        .filter(x -> x.getName().equalsIgnoreCase(technology.getName())).collect(Collectors.toList());

                if (techs.isEmpty()) {
                    finalCollection.add(technology);
                }
            });

            repository.saveAll(finalCollection);
        } catch (Exception e) {
            throw new CSVUploadException();
        }

        return duplicateTechnologies;
    }
}