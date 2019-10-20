import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RiotChestFinder {

    private String developmentKey;

    private int requestFiredIn2Minutes;
    private LocalTime timeWhenRequestReset;
    private final int MAX_REQUEST_PER_2_MINUTES = 100;
    private Map<Long, String> champIdList;

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("developmentKey.txt"));

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey.txt Could not be found");
        }

        developmentKey = reader.nextLine();
        reader.close();

        requestFiredIn2Minutes = 0;
        timeWhenRequestReset = LocalTime.now().plusMinutes(2);

        champIdList = new HashMap<>();

        reader = new Scanner(new File("championIds.txt"));

        while(reader.hasNextLine()) {
            String line = reader.nextLine();

            String championName = line.substring(0, line.indexOf(' '));
            Long id = new Long(line.substring(line.lastIndexOf(' ') + 1));

            champIdList.put(id, championName);
        }
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

            return new Summoner( (String) jason.get("name"), (String) jason.get("id"), (String) jason.get("accountId"), false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getNoChests(String summonerId) {
        //uwu lemme do this okie
        ArrayList<String> names = new ArrayList<>();
        try {
            URL gettingChampions = new URL(Summoner.CHAMPIONS_REQUEST + summonerId + "?api_key=" + developmentKey);

            String info = getRequest(gettingChampions);

            JSONArray championList = (JSONArray) new JSONParser().parse(info);

            for(int i = 0; i < championList.size(); i++) {
                JSONObject champion = (JSONObject) championList.get(i);

                if (!(boolean) champion.get("chestGranted")) {
                    names.add(champIdList.get(champion.get("championId")));
                }
            }

        }catch (MalformedURLException e){
            System.out.println("Bad Request");
        }catch (ParseException e){
            System.out.println("JSON failed");
        }


        return names;
    }

    /**
     * Makes a request at the given URL and returns a String representation of the information back.
     * @param url
     * @return null if either an error is thrown or if the rate limit is exceeded.
     */
    private String getRequest(URL url) {

        //Resetting your amount of calls you get to make
        if(LocalTime.now().isAfter(timeWhenRequestReset)) {
            requestFiredIn2Minutes = 0;
            timeWhenRequestReset = LocalTime.now().plusMinutes(2);
        }

        try {
            if(!rateLimitExceeded()) {
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();

                connect.setRequestMethod("GET");
                connect.setDoInput(true);

                InputStream in = connect.getInputStream();

                requestFiredIn2Minutes++;

                return new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
            }
            else {
                return null;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean rateLimitExceeded() {
        return requestFiredIn2Minutes > MAX_REQUEST_PER_2_MINUTES;
    }
}
