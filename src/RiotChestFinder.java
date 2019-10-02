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
