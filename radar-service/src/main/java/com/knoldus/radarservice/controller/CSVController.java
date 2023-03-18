package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.exception.CSVUploadException;
import com.knoldus.radarservice.model.Technology;
import com.knoldus.radarservice.response.Response;
import com.knoldus.radarservice.response.ResponseStatus;
import com.knoldus.radarservice.response.Responses;
import com.knoldus.radarservice.service.CSVHelper;
import com.knoldus.radarservice.service.CSVService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * This Controller uploads csv file to the database
 */
@RestController
@RequestMapping("/knoldus-backend/rest/radar-service/technology")
@AllArgsConstructor
@Api("Technology API endpoints. Used for uploading the csv file containing information about the technologies")
public class CSVController {

    private static final String SUCCESS_MESSAGE = "Uploaded the file successfully: ";
    private static final String PARTAILY_UPLOADED = "Contains few duplicate records please check those records";

    CSVService fileService;

    @ApiOperation(
            value = "Upload CSV file."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully uploaded csv file."
            ),
            @ApiResponse(
                    code = 400,
                    message = "The request payload is incorrect, or there is some problem with the csv file"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @PostMapping(value = "/csv/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Collection<Technology>> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = SUCCESS_MESSAGE + file.getOriginalFilename();
        Collection<Technology> failedTechs;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                failedTechs = fileService.save(file);
                if (!failedTechs.isEmpty()) {
                    message = PARTAILY_UPLOADED + file.getOriginalFilename();
                }
                return Responses.ok(failedTechs).setStatusCode(ResponseStatus.OK.getCode()).setStatusText(message);
            } catch (Exception e) {
                throw new CSVUploadException();
            }
        }
        throw new CSVUploadException();
    }
}
