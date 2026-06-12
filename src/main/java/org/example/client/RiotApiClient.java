package org.example.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RiotApiClient {

    private final HttpClient httpClient;
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RiotApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    public Account getAccountByRiotId(String region, String gameName, String tagLine)  throws IOException, InterruptedException {
        String url = String.format(
            "https://%s.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s",
            region, gameName, tagLine
        );

        return objectMapper.readValue(sendRequest(url), Account.class);
    }

    public Summoner getSummonerByPuuid(String platform, String puuid) throws IOException, InterruptedException {
        String url = String.format(
                "https://%s.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/%s",
                platform, puuid
        );

        return objectMapper.readValue(sendRequest(url), Summoner.class);
    }
    public List<LeagueEntry> getLeagueEntries(String platform, String puuid) throws IOException, InterruptedException {
        String url = String.format(
                "https://%s.api.riotgames.com/lol/league/v4/entries/by-puuid/%s",
                platform, puuid
        );

        return objectMapper.readValue(sendRequest(url), new TypeReference<>() {
        });
    }
    public List<String> getMatchIds(String region, String puuid, int count) throws IOException, InterruptedException {
        String url = String.format(
                "https://%s.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?count=%d",
                region, puuid, count
        );

        return objectMapper.readValue(sendRequest(url), new TypeReference<>() {
        });
    }
    public MatchDto getMatch(String region, String matchId) throws IOException, InterruptedException {
        String url = String.format(
                "https://%s.api.riotgames.com/lol/match/v5/matches/%s",
                region, matchId
        );

        return objectMapper.readValue(sendRequest(url), MatchDto.class);
        }

    private String sendRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Riot-Token", apiKey)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Riot API zwróciło błąd " + response.statusCode() + ": " + response.body());
        }

        return response.body();
    }
}
