package com.trimble.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LetterMetricsModel {

    @JsonProperty("letra")
    private Character letter;

    @JsonProperty("quantidade")
    private Integer amount;
}
