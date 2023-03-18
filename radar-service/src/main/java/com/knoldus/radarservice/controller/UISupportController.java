package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.model.QuarterType;
import com.knoldus.radarservice.model.StudioType;
import com.knoldus.radarservice.model.UISupportDropDown;
import com.knoldus.radarservice.response.Response;
import com.knoldus.radarservice.response.Responses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is designed to support ui like drop-down for studio and quarters
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/knoldus-backend/rest/radar-service/ui-support")
@Api("UI Support API endpoints. Used for retrieving ui drop-down elements.")
public class UISupportController {

    @ApiOperation(
            value = "Successfully Retrieved the studios."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all studios."
            ),
            @ApiResponse(
                    code = 400,
                    message = "Unable to process request"
            )
    })
    @GetMapping(path = "/studios", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<UISupportDropDown> getStudios() {
        List<String> quarterTypes = Stream.of(StudioType.values()).map(Enum::name).collect(
                Collectors.toList());
        return Responses.ok(new UISupportDropDown(quarterTypes));
    }

    @ApiOperation(
            value = "Successfully Retrieved the quarters."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all quarters."
            ),
            @ApiResponse(
                    code = 400,
                    message = "Unable to process request"
            )
    })
    @GetMapping(path = "/quarters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<UISupportDropDown> getQuarters() {
        List<String> quarterTypes = Stream.of(QuarterType.values()).map(Enum::name).collect(
                Collectors.toList());
        return Responses.ok(new UISupportDropDown(quarterTypes));
    }
}
