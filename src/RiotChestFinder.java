import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RiotChestFinder {

    private String developmentKey;
    private URL url;
    private ArrayList<RiotID> ids;
    private ArrayList<RiotChampion> champions;
    private Map<String, Integer>

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("developmentKey.txt"));

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey Could not be found");
        }

        developmentKey = reader.nextLine();
        System.out.println(developmentKey);

        reader.close();

        ids = new ArrayList<>();
        champions = new ArrayList<>();

    }

    /**
     * get a summoner or RiotId with their name
     * if the summoner does not exist, will make
     * new summoner and will return that summoner
     *
     * @param summonerName
     * @return
     */
    public RiotID getRiotID(String summonerName) {
        for (RiotID id : ids) {
            if(id.getSummonerName().equals(summonerName)) {
                return id;
            }
        }
        try {
            URL gettingIDs = new URL(RiotID.ID_REQUEST + summonerName + "?api_key=" + developmentKey);
            String riotIdInfo = getRequest(gettingIDs);

            JSONObject jason = (JSONObject) new JSONParser().parse(riotIdInfo);

            return new RiotID( (String) jason.get("Summoner Name"), (String) jason.get("SummonerId"), (String) jason.get("accountId"));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Will get the information about a champion
     * that a specific player plays
     *
     * @param summonerId
     * @param championName will use this to get championId for the URL
     * @return
     */
    public RiotChampion getRiotChampion(String summonerId, String championName) throws FileNotFoundException {
        for(RiotChampion champion: champions){
            if(champion.getChampionId().equals(getChampionIdFromFile(championName))) {
                System.out.println("Champion exists");
                return champion;
            }
        }
        try{
            URL gettingChampion = new URL(RiotChampion.ID_REQUEST + summonerId +
                    "/by-champion/" + getChampionIdFromFile(championName) + "?api_key=" + developmentKey);
            InputStream in  = getRequest(gettingChampion);
            InputStreamReader reader = new InputStreamReader(in);

            String response = "";
            boolean hasChest = false;
            long lastPlayTime = 0000000000000;
            int number = reader.read();
            int i = 0;

            while(number != -1 && i < 14) {
                char at = (char) number;
                response +=(at);
                if(at == ':' || at == ',') {
                    if(i == 7)
                        lastPlayTime = Long.parseLong(response.substring(0, response.length() - 1));
                    else if(i == 13)
                        hasChest = Boolean.parseBoolean(response.substring(0, response.length() - 1));

                    response = "";
                    i++;
                }
                number = reader.read();
            }

           return new RiotChampion(getChampionIdFromFile(championName), championName, lastPlayTime, hasChest);


        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRequest(URL url)  {
        try {
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");
            connect.setDoInput(true);
            InputStream in = connect.getInputStream();

            return new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
