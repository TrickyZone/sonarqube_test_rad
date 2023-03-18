package com.knoldus.radarservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

/**
 * The data model representing a Technology, which cover common information such as name, ring etc.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class TechnologyRequest {


    @ApiModelProperty(
            value = "The name of the technology.",
            required = true,
            example = "java",
            position = 1
    )
    @NotBlank
    @NonNull
    String name;

    @ApiModelProperty(
            value = "The ring of the technology.",
            required = true,
            example = "assess",
            position = 2
    )
    @NotBlank
    @NonNull
    String ring;

    @ApiModelProperty(
            value = "The quadrant of the technology.",
            required = true,
            example = "Languages and Frameworks",
            position = 3
    )
    @NotBlank
    @NonNull
    String quadrant;

    @ApiModelProperty(
            value = "The name of the technology.",
            required = true,
            example = "true",
            position = 4
    )
    Boolean isNew;

    @ApiModelProperty(
            value = "The description of the technology.",
            required = true,
            example = "Java is a plausible choice for new products (due to its rich ecosystem)",
            position = 5
    )
    @NotBlank
    @NonNull
    String description;


    @ApiModelProperty(
            value = "The description of the studio.",
            required = true,
            example = "JAVA_STUDIO",
            allowableValues = "JAVA_STUDIO, SCALA_STUDIO, DEVOPS_STUDIO, FRONTEND_STUDIO",
            position = 6
    )
    @NotNull
    @NonNull
    StudioType studio;


    @ApiModelProperty(
            value = "The description of the quarter.",
            required = true,
            example = "QUARTER_1",
            allowableValues = "QUARTER_1, QUARTER_2, QUARTER_3, QUARTER_4",
            position = 7
    )
    @NotNull
    @NonNull
    QuarterType quarter;

    @ApiModelProperty(
            value = "The name of the technology.",
            required = true,
            example = "true",
            position = 8
    )
    Boolean active;

    @ApiModelProperty(
            value = "The description of the year.",
            required = true,
            example = "2022",
            position = 9
    )
    @NotBlank
    @NonNull
    String year;

    @ApiModelProperty(value = "The description of the alternative technology",position = 10)
    String alternateTechnology;

    @ApiModelProperty(value = "The url of the given technology reference",position = 11)
    String githubUrl;

}