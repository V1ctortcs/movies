package com.trimble.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterMetricsModel {

    @JsonProperty("letra")
    private Character letter;

    @JsonProperty("quantidade")
    private Integer amount;
}
