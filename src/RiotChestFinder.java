import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RiotChestFinder {

    private String developmentKey;

    private int requestFiredIn2Minutes;
    private LocalTime timeWhenRequestReset;
    private final int MAX_REQUEST_PER_2_MINUTES = 100;

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("developmentKey.txt"));

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey.txt Could not be found");
        }

        developmentKey = reader.nextLine();
        reader.close();

        requestFiredIn2Minutes = 0;
        timeWhenRequestReset = LocalTime.now().plusMinutes(2);
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

    public String[] getNoChests(){
        //uwu lemme do this okie
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
            if(requestFiredIn2Minutes < MAX_REQUEST_PER_2_MINUTES) {
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
        if (requestFiredIn2Minutes > MAX_REQUEST_PER_2_MINUTES) {
            return true;
        }
        return false;
    }
}
