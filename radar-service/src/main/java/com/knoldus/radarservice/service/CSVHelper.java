package com.knoldus.radarservice.service;

import com.knoldus.radarservice.exception.CSVUploadException;
import com.knoldus.radarservice.model.Technology;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import static com.knoldus.radarservice.model.QuarterType.QUARTER_1;
import static com.knoldus.radarservice.model.StudioType.JAVA_STUDIO;


public class CSVHelper {
    public static final String TYPE = "text/csv";

    private CSVHelper() {}

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Technology> csvToTechnologies(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.builder().setHeader()
                                .setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build())) {

            List<Technology> technologies = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                Technology technology = new Technology(
                        UUID.randomUUID(),
                        csvRecord.get("name"),
                        csvRecord.get("ring"),
                        csvRecord.get("quadrant"),
                        Boolean.parseBoolean(csvRecord.get("isNew")),
                        csvRecord.get("description"),
                        csvRecord.isSet("studio") ? csvRecord.get("studio") : JAVA_STUDIO.name(),
                        csvRecord.isSet("quarter") ? csvRecord.get("quarter") : QUARTER_1.name(),
                        !csvRecord.isSet("active") || Boolean.parseBoolean(csvRecord.get("active")),
                        csvRecord.isSet("year") ? csvRecord.get("year") : "2022",
                        Timestamp.from(ZonedDateTime.now().toInstant()),
                        Timestamp.from(ZonedDateTime.now().toInstant()),
                        csvRecord.isSet("alternate_technology")?csvRecord.get("alternate_technology"):null,
                        csvRecord.isSet("github_url")?csvRecord.get("github_url"):null

                );

                technologies.add(technology);
            }

            return technologies;
        } catch (Exception e) {
            throw new CSVUploadException();
        }
    }
}