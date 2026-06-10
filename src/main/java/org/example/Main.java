package org.example;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) throws Exception {
        String region   = args[0];
        String gameName = args[1];
        String tagLine  = args[2];

        // Wczytaj klucz API z pliku .env (lub zmiennej środowiskowej RIOT_API_KEY)
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Brak klucza API_KEY w pliku .env lub zmiennych środowiskowych.");
            System.exit(1);
        }

        RiotApiClient client = new RiotApiClient(apiKey);

        System.out.printf("Pobieranie danych dla: %s#%s (region: %s)%n", gameName, tagLine, region);
        System.out.println("---");

        String json = client.getAccountByRiotId(region, gameName, tagLine);
        System.out.println(json);
    }
}
