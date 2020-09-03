package com.trimble.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieModel {

    @JsonProperty("id")
    private Integer codMovie;

    @JsonProperty("title")
    private String titleMovie;

    @JsonProperty("release_date")
    private LocalDate releaseDateMovie;

    @JsonProperty("synopsis")
    private String synopsisMovie;

    @JsonProperty("user_rating")
    private Integer userRatingMovie;

}
