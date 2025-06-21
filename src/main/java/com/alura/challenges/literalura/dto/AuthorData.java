package com.alura.challenges.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(
        String name,
        @JsonAlias("birth_year") Integer birth,
        @JsonAlias("death_year") Integer death
) {
}
