package com.example.loltracker.client;

import com.example.loltracker.model.Account;
import com.example.loltracker.model.LeagueEntry;
import com.example.loltracker.model.Summoner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RiotApiClient {

    private final RestClient restClient;

    public RiotApiClient(@Value("${riot.api-key}") String apiKey) {
        this.restClient = RestClient.builder()
                .defaultHeader("X-Riot-Token", apiKey)
                .build();
    }

    public Account getAccountByRiotId(String region, String gameName, String tagLine) {
        return restClient.get()
                .uri("https://{region}.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}",
                        region, gameName, tagLine)
                .retrieve()
                .body(Account.class);
    }

    public Summoner getSummonerByPuuid(String platform, String puuid) {
        return restClient.get()
                .uri("https://{platform}.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/{puuid}",
                        platform, puuid)
                .retrieve()
                .body(Summoner.class);
    }

    public List<LeagueEntry> getLeagueEntries(String platform, String puuid) {
        return restClient.get()
                .uri("https://{platform}.api.riotgames.com/lol/league/v4/entries/by-puuid/{puuid}",
                        platform, puuid)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<String> getMatchIds(String region, String puuid, int count) {
        return restClient.get()
                .uri("https://{region}.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids?count={count}",
                        region, puuid, count)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
