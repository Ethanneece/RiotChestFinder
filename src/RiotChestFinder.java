import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RiotChestFinder {

    private String developmentKey;
    private URL url;
    private ArrayList<RiotID> ids;
    private ArrayList<RiotChampion> champions;

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("developmentKey"));

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey Could not be found");
        }

        developmentKey = reader.nextLine();
        System.out.println(developmentKey);

        reader.close();

        ids = new ArrayList<>();
        champions = new ArrayList<>();

    }

    public RiotID getRiotID(String summonerName) {
        for (RiotID id : ids) {
            if(id.getSummonerName().equals(summonerName)) {
                return id;
            }
        }

        try {
            URL gettingIDS = new URL(RiotID.ID_REQUEST + summonerName + "?api_key=" + developmentKey);
            System.out.println(gettingIDS);
            InputStream in  = getRequest(gettingIDS);
            System.out.println(in.read());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private InputStream getRequest(URL url)  {
        try {
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");
            connect.setDoInput(true);
            InputStream in = connect.getInputStream();
            BufferedReader hi = new BufferedReader(new InputStreamReader(in));
            System.out.println(hi);
            int status = connect.getResponseCode();
            System.out.println(status);
            return in;

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

/*
    URL gettingsChampions = new URL("https://na1.api.riotgames.com/lol/" +
            "champion-mastery/v4/champion-masteries/by-summoner/" + summonerId + "/by-champion/" + championID + "?api_key=" + apiKey);
     URL gettingsIDs = new URL("https://na1.api.riotgames.com/lol/" +
            "summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + apiKey);
 */