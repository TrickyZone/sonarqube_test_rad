package com.knoldus.radarservice.controller;

import com.knoldus.radarservice.model.*;
import com.knoldus.radarservice.response.Response;
import com.knoldus.radarservice.response.ResponseStatus;
import com.knoldus.radarservice.response.Responses;
import com.knoldus.radarservice.service.TechnologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * This controller is designed to create, update, get and delete the technology.
 */
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/knoldus-backend/rest/radar-service/technology")
@Api("Technology API endpoints. Used for creating, updating, retrieving, or removing technology from a Knoldus Radar.")
public class TechnologyController {
    private static final String SUCCESS_MESSAGE = "Successfully saved all technologies";
    private static final String PARTIALY_UPLOADED = "Contains few duplicate records please check those records";

    @Autowired
    private TechnologyService technologyService;

    @ApiOperation(
            value = "Successfully Register the technology into the radar."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully created."
            ),
            @ApiResponse(
                    code = 400,
                    message = "The request payload is incorrect, the specified values for the technology already exist"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Technology> createTechnology(@Valid @RequestBody TechnologyRequest technologyRequest) {
        Technology technology = technologyService.createTechnology(technologyRequest);
        return Responses.ok(technology);
    }

    @ApiOperation(
            value = "Successfully Register the multiple technologies into the radar."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully created."
            ),
            @ApiResponse(
                    code = 400,
                    message = "The request payload is incorrect, the specified values for the technology already exist"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @PostMapping(path = "/creates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Collection<Technology>> createTechnologies(
            @Valid @RequestBody List<TechnologyRequest> technologiesRequest) {

        String message = SUCCESS_MESSAGE;

        Collection<Technology> failedTechs = technologyService.createTechnologies(technologiesRequest);

        if (!failedTechs.isEmpty()) {
            message = PARTIALY_UPLOADED;
        }

        return Responses.ok(failedTechs).setStatusCode(ResponseStatus.OK.getCode()).setStatusText(message);
    }

    @ApiOperation(
            value = "Update the technology if exist"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "All the fields related to the technology "
            ),
            @ApiResponse(
                    code = 404,
                    message = "Unable to find the given technology."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @PutMapping("/update")
    public Response<Technology> updateTechnology(@RequestBody UpdateTechnologyRequest request) {

        var technology = technologyService.update(request);
        return Responses.ok(technology);
    }

    @ApiOperation(
            value = "Get all the technologies aligned with the knoldus radar."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Retrieves or fetches the list of the technologies successfully."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @GetMapping(path = "/radar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<TechnologyResponse>> getTechnologyForRadar(@RequestParam(required = false) String quarter,
                                                                    @RequestParam(required = false) String studio) {

        List<TechnologyResponse> technologies = technologyService.getTechnologyForRadar(quarter, studio);

        return Responses.ok(technologies);
    }

    @ApiOperation(
            value = "Delete the technology if exist"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Technology is deleted with the given Id"
            ),
            @ApiResponse(
                    code = 404,
                    message = "Unable to find the given technology."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @DeleteMapping("/delete/{technologyId}")
    public Response<String> deleteResponse(@PathVariable UUID technologyId) {
        technologyService.delete(technologyId);
        return Responses.ok();
    }

    @ApiOperation(
            value = "Get all the technologies."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Retrieves or fetches the technologies for the specific quarter successfully."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Technology>> getTechnology() {

        List<Technology> technologies = technologyService.getTechnology();

        return Responses.ok(technologies);
    }


    @ApiOperation(
            value = "Get all the studio in the knoldus radar."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Retrieves or fetches the list of the studio successfully."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @GetMapping(path = "/studio", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<StudioResponse>> getAllStudio() {

        List<StudioResponse> technologies = technologyService.getAllStudio();

        return Responses.ok(technologies);
    }

    @ApiOperation(
            value = "Get all the radar in the knoldus radar."
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Retrieves or fetches the list of the quarter successfully."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Something went terribly wrong."
            )
    })
    @GetMapping(path = "/quarter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<QuarterResponse>> getAllQuarter() {

        List<QuarterResponse> technologies = technologyService.getAllQuarter();

        return Responses.ok(technologies);
    }

    @ApiOperation(value = "Get all the technology into the radar with Area ,Quadrant and Studio.")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully fetched."), @ApiResponse(code = 500, message = "Something went terribly wrong.")})
    @GetMapping(path = "/techfilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<TechnologyResponse>> getTechnologyWithRespectOfAreaAndQuadrantAndStudio(@RequestParam(value = "quarter", required = false) String quarter,
                                                                                                 @RequestParam(value = "studio", required = false) String studio,
                                                                                                 @RequestParam(value = "quadrant", required = false) String area) {
        log.info("Search filter criteria value of Quadrant : {} Studio : {} Quarter : {}", area, studio, quarter);
        List<TechnologyResponse> technologyResponses = technologyService
                .fetchTechnologyWithRespectOfAreaAndQuadrantAndStudio(area,quarter,studio);
        return Responses.ok(technologyResponses);
    }

    @ApiOperation(value = "find technology from radar on basis of name.")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully fetched."), @ApiResponse(code = 500, message = "Something went terribly wrong.")})
    @GetMapping(path = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<TechnologyResponse>> getTechnologyWithRespectOfName(@RequestParam(value = "name", required = false) String name) {

        List<TechnologyResponse> technologyResponses = technologyService
                .fetchTechnologyWithRespectOfName(name);
        return Responses.ok(technologyResponses);
    }

}
