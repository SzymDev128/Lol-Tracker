package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.client.RiotApiClient;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String region   = args[0];
        String platform = args[1];
        String gameName = args[2];
        String tagLine  = args[3];

        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Brak klucza API_KEY w pliku .env lub zmiennych środowiskowych.");
            System.exit(1);
        }

        RiotApiClient client = new RiotApiClient(apiKey);

        System.out.printf("Pobieranie danych dla: %s#%s (region: %s) (platform: %s)%n", gameName, tagLine, region, platform);

        Account account = client.getAccountByRiotId(region, gameName, tagLine);
        Summoner summoner = client.getSummonerByPuuid(platform, account.puuid());
        List<LeagueEntry> rank = client.getLeagueEntries(platform, account.puuid());
        List<String> matches = client.getMatchIds(region, account.puuid(),5);

        List<MatchSummary> summaries = new ArrayList<>();

        for(String matchId : matches){
            MatchDto match = client.getMatch(region,matchId);
            MatchDto.Participant p = match.info().participants().stream()
                    .filter(part -> part.puuid().equals(account.puuid()))
                    .findFirst()
                    .orElseThrow();
            summaries.add(new MatchSummary(matchId,p.championName(),p.win(),p.kills(),p.deaths(),p.assists(),p.totalMinionsKilled() + p.neutralMinionsKilled(),match.info().gameDuration()));

        }
        double avgKills = summaries.stream().mapToInt(MatchSummary::kills).average().orElse(0);
        double avgDeaths = summaries.stream().mapToInt(MatchSummary::deaths).average().orElse(0);
        double avgAssists = summaries.stream().mapToInt( MatchSummary::assists ).average().orElse(0);
        double winRatio = (summaries.stream().filter(MatchSummary::win).count() / (double) matches.size()) * 100;

        summaries.forEach(summary -> {
            String kda = summary.kills() + "/" + summary.deaths() + "/" + summary.assists();

            System.out.printf("Summary: %s %n", summary.matchId());
            System.out.printf("\t Win: %b %n", summary.win());
            System.out.printf("\t Champion: %s %n", summary.championName());
            System.out.printf("\t KDA: %s %n", kda);
            System.out.printf("\t Farm: %d %n", summary.cs());
        });

        System.out.printf("WIN RATIO: %.1f%% %n", winRatio);
        System.out.printf("KDA: %.1f/%.1f/%.1f %n", avgKills, avgDeaths, avgAssists);
    }
}
