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

    public RiotID getRiotID(String summonerName) {
        for (RiotID id : ids) {
            if(id.getSummonerName().equals(summonerName)) {
                return id;
            }
        }
        try {
            URL gettingIDS = new URL(RiotID.ID_REQUEST + summonerName + "?api_key=" + developmentKey);
            InputStream in  = getRequest(gettingIDS);

            InputStreamReader reader = new InputStreamReader(in);

            String response = "";
            String[] info = new String[2];
            int number = reader.read();
            int i = 0;

            while(number != -1 && i < info.length * 2) {
                char at = (char) number;
                response +=(at);

                if(at == ':' || at == ',') {
//                    System.out.println(response);
                    if (i % 2 != 0) {
                        info[i/2] = response.substring(1, response.length() - 2);
                    }else
                        response = "";
                    i++;
                }

                number = reader.read();
            }

//            for(String x: info)
//                System.out.println(x);

            String summonerId = info[0];
            String accountId = info[1];
            RiotID summoner = new RiotID(summonerName, summonerId, accountId);


            return summoner;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RiotChampion getRiotChampion(String summonerId, String championId){
        for(RiotChampion champion: champions){
            if(champion.getChampionId().equals(championId)){
                return champion;
            }
        }
        try{
            URL gettingChampion = new URL(RiotChampion.ID_REQUEST + summonerId +
                    "/by-champion/" + championId + "?api_key=" + developmentKey);
            System.out.println(gettingChampion);
            InputStream in  = getRequest(gettingChampion);
            System.out.println(in.read());

        }catch (MalformedURLException e){
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
            //System.out.println(hi);
            int status = connect.getResponseCode();
            //System.out.println(status);
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