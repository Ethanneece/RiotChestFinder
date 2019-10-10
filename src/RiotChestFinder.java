import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RiotChestFinder {

    private String developmentKey;
    private URL url;

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("developmentKey.txt"));

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey Could not be found");
        }

        developmentKey = reader.nextLine();
        reader.close();
    }

    /**
     * get a summoner or RiotId with their name
     * if the summoner does not exist, will make
     * new summoner and will return that summoner
     *
     * @param summonerName
     * @return
     */
    public Summoner getSummoner(String summonerName) {

        if(summonerName.matches("^[0-9\\\\p{L} _\\\\.]+$")) {
            System.out.println("Bad Summoner request" );
            return null;
        }

        try {
            URL gettingIDs = new URL(Summoner.ACCOUNT_ID_REQUEST + summonerName + "?api_key=" + developmentKey);
            String riotIdInfo = getRequest(gettingIDs);

            JSONObject jason = (JSONObject) new JSONParser().parse(riotIdInfo);

            return new Summoner( (String) jason.get("name"), (String) jason.get("id"), (String) jason.get("accountId"));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    /**
//     * Will get the information about a champion
//     * that a specific player plays
//     *
//     * @param summonerId
//     * @param championName will use this to get championId for the URL
//     * @return
//     */
//    public RiotChampion getRiotChampion(String summonerId, String championName) throws FileNotFoundException {
//        for(RiotChampion champion: champions) {
//            if(champion.getChampionId().equals(RiotChampion.getChampionIdFromFile(championName))) {
//                System.out.println("Champion exists");
//                return champion;
//            }
//        }
//        try{
//            URL gettingChampion = new URL(RiotChampion.ID_REQUEST + summonerId +
//                    "/by-champion/" + RiotChampion.getChampionIdFromFile(championName) + "?api_key=" + developmentKey);
//            InputStream in  = getRequest(gettingChampion);
//            InputStreamReader reader = new InputStreamReader(in);
//
//            String response = "";
//            boolean hasChest = false;
//            long lastPlayTime = 0000000000000;
//            int number = reader.read();
//            int i = 0;
//
//            while(number != -1 && i < 14) {
//                char at = (char) number;
//                response +=(at);
//                if(at == ':' || at == ',') {
//                    if(i == 7)
//                        lastPlayTime = Long.parseLong(response.substring(0, response.length() - 1));
//                    else if(i == 13)
//                        hasChest = Boolean.parseBoolean(response.substring(0, response.length() - 1));
//
//                    response = "";
//                    i++;
//                }
//                number = reader.read();
//            }
//
//           return new RiotChampion(getChampionIdFromFile(championName), championName, lastPlayTime, hasChest);
//
//
//        } catch (MalformedURLException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private String getRequest(URL url)  {
        try {
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");
            connect.setDoInput(true);
            InputStream in = connect.getInputStream();

            System.out.println(connect.getResponseCode());

            return new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
