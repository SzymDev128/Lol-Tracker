package org.example.model;

public record MatchSummary(
        String matchId,
        String championName,
        boolean win,
        int kills,
        int deaths,
        int assists,
        int cs,
        long gameDuration
) {}
