package com.knoldus.radarservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.core.lang.NonNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The data model representing a Technology to be updated, which cover common information such as name, ring etc.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTechnologyRequest {


    @ApiModelProperty(
            value = "The id of the technology.",
            required = true,
            example = "40bb5bdf-80d2-47b5-a615-178e14c65760",
            position = 1
    )
    @NotBlank
    @NonNull
    UUID id;

    @ApiModelProperty(
            value = "The name of the technology.",
            example = "java",
            position = 2
    )
    String name;

    @ApiModelProperty(
            value = "The ring of the technology.",
            example = "assess",
            position = 3
    )
    String ring;

    @ApiModelProperty(
            value = "The quadrant of the technology.",
            example = "Languages and Frameworks",
            position = 4
    )
    String quadrant;

    @ApiModelProperty(
            value = "The name of the technology.",
            example = "true",
            position = 5
    )
    Boolean isNew;

    @ApiModelProperty(
            value = "The description of the technology.",
            example = "Java is a plausible choice for new products (due to its rich ecosystem)",
            position = 6
    )
    String description;

    @ApiModelProperty(
            value = "The description of the studio.",
            required = true,
            example = "JAVA_STUDIO",
            allowableValues = "JAVA_STUDIO, SCALA_STUDIO, DEVOPS_STUDIO, FRONTEND_STUDIO",
            position = 7
    )
    StudioType studio;

    @ApiModelProperty(
            value = "The description of the quarter.",
            example = "Quarter-1",
            position = 8
    )
    QuarterType quarter;

    @ApiModelProperty(
            value = "The name of the technology.",
            example = "true",
            position = 9
    )
    Boolean active;

    @ApiModelProperty(
            value = "The description of the year.",
            example = "2022",
            position = 10
    )
    String year;
}