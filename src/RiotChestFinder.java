import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RiotChestFinder {

    private String developmentKey;
    private URL url;
    private ArrayList<RiotID> ids;
    private ArrayList<RiotChampion> champions;

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner("DevelopmentKey");

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey Could not be found");
        }

        developmentKey = reader.nextLine();


    }

    private String getSummonerID(String summonerName) {
        for (RiotID id : ids) {
            if(id.getSummonerName().equals(summonerName)) {
                return id.getSummonerId();
            }
        }

        ids.add(new RiotID(summonerName));
    }

    private String getAccountID(String summonerName) {
        for (RiotID id : ids) {
            if(id.getSummonerName().equals(summonerName)) {
                return id.getAccountId();
            }
        }

        ids.add(new RiotID(summonerName));
    }

    public String getDevelopmentKey() {
        return developmentKey;
    }
}

/*
    URL gettingsChampions = new URL("https://na1.api.riotgames.com/lol/" +
            "champion-mastery/v4/champion-masteries/by-summoner/" + summonerId + "/by-champion/" + championID + "?api_key=" + apiKey);
     URL gettingsIDs = new URL("https://na1.api.riotgames.com/lol/" +
            "summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + apiKey);
 */