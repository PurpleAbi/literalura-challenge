package com.alura.challenges.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        String title,
        List<AuthorData> authors,
        @JsonAlias("summaries") List<String> summary,
        List<String> languages,
        @JsonAlias("download_count") Integer downloads
) {
}
