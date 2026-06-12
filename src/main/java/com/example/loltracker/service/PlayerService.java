package com.example.loltracker.service;

import com.example.loltracker.client.RiotApiClient;
import com.example.loltracker.dto.PlayerProfileResponse;
import com.example.loltracker.model.Account;
import com.example.loltracker.model.LeagueEntry;
import com.example.loltracker.model.Summoner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final RiotApiClient riotApiClient;

    public PlayerService(RiotApiClient riotApiClient) {
        this.riotApiClient = riotApiClient;
    }

    public PlayerProfileResponse getPlayerProfile(String platform, String gameName, String tagLine) {
        String region = platformToRegion(platform);
        Account account = riotApiClient.getAccountByRiotId(region, gameName, tagLine);
        Summoner summoner = riotApiClient.getSummonerByPuuid(platform, account.puuid());
        List<LeagueEntry> entries = riotApiClient.getLeagueEntries(platform, account.puuid());

        LeagueEntry soloRank = entries.stream()
                .filter(e -> e.queueType().equals("RANKED_SOLO_5x5"))
                .findFirst()
                .orElse(null);

        return new PlayerProfileResponse(
                account.gameName(),
                account.tagLine(),
                summoner.summonerLevel(),
                summoner.profileIconId(),
                soloRank
        );
    }

    private String platformToRegion(String platform) {
        return switch (platform.toLowerCase()) {
            case "euw1", "eun1", "tr1", "ru" -> "europe";
            case "na1", "br1", "la1", "la2" -> "americas";
            case "kr", "jp1" -> "asia";
            default -> throw new IllegalArgumentException("Unknown platform: " + platform);
        };
    }
}
