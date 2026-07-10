package com.example.loltracker.service;

import com.example.loltracker.client.RiotApiClient;
import com.example.loltracker.dto.PlayerProfileResponse;
import com.example.loltracker.entity.Player;
import com.example.loltracker.entity.RankEntity;
import com.example.loltracker.model.Account;
import com.example.loltracker.model.LeagueEntry;
import com.example.loltracker.model.Summoner;
import com.example.loltracker.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayerService {

    private final RiotApiClient riotApiClient;
    private final PlayerRepository playerRepository;

    public PlayerService(RiotApiClient riotApiClient, PlayerRepository playerRepository) {
        this.riotApiClient = riotApiClient;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public PlayerProfileResponse getPlayerProfile(String platform, String gameName, String tagLine) {
        String region = platformToRegion(platform);
        Account account = riotApiClient.getAccountByRiotId(region, gameName, tagLine);
        Summoner summoner = riotApiClient.getSummonerByPuuid(platform, account.puuid());
        List<LeagueEntry> entries = riotApiClient.getLeagueEntries(platform, account.puuid());

        LeagueEntry soloRank = entries.stream()
                .filter(e -> e.queueType().equals("RANKED_SOLO_5x5"))
                .findFirst()
                .orElse(null);

        Player player = playerRepository.findByPuuid(account.puuid())
                .orElseGet(() ->{
                    Player p = new Player();
                    p.setPuuid(account.puuid());
                    p.setPlatform(platform);
                    return p;
                });

        player.setGameName(account.gameName());
        player.setTagLine(account.tagLine());
        player.setSummonerLevel(summoner.summonerLevel());
        player.setProfileIconId(summoner.profileIconId());
        player.setLastUpdated(LocalDateTime.now());

        player.getRankEntries().clear();


        for(LeagueEntry entry : entries) {
            RankEntity rankEntity = new RankEntity();
            rankEntity.setQueueType(entry.queueType());
            rankEntity.setTier(entry.tier());
            rankEntity.setRank(entry.rank());
            rankEntity.setLeaguePoints(entry.leaguePoints());
            rankEntity.setWins(entry.wins());
            rankEntity.setLosses(entry.losses());
            rankEntity.setPlayer(player);
            player.getRankEntries().add(rankEntity);
        }


        playerRepository.save(player);

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
