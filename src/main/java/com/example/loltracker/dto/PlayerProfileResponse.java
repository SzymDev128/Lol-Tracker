package com.example.loltracker.dto;

import com.example.loltracker.model.LeagueEntry;

public record PlayerProfileResponse(
        String gameName,
        String tagLine,
        long summonerLevel,
        int profileIconId,
        LeagueEntry soloRank
) {}
