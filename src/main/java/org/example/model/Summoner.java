package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Summoner(
        String puuid,
        int profileIconId,
        long summonerLevel
) {}
