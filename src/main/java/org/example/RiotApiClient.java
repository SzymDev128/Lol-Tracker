package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RiotApiClient {

    private final HttpClient httpClient;
    private final String apiKey;

    public RiotApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getAccountByRiotId(String region, String gameName, String tagLine)
            throws IOException, InterruptedException {

        // ACCOUNT-V1 używa routing regionalnego: europe, americas, asia, esports
        String url = String.format(
            "https://%s.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s",
            region, gameName, tagLine
        );

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
