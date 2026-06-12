package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LeagueEntry(
        String queueType,
        String tier,
        String rank,
        int leaguePoints,
        int wins,
        int losses
) {}
