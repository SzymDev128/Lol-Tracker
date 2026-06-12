package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MatchDto(Metadata metadata, Info info) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Metadata(String matchId, List<String> participants) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Info(long gameDuration, int queueId, List<Participant> participants) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Participant(
            String puuid,
            String championName,
            int kills,
            int deaths,
            int assists,
            int totalMinionsKilled,
            int neutralMinionsKilled,
            boolean win
    ) {}
}
