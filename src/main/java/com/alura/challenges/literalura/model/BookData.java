package com.alura.challenges.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(
        Long id,
        String title,
        String authors,
        @JsonAlias("summaries") String summary,
        String languages,
        @JsonAlias("download_count") Long downloads
) {
}
