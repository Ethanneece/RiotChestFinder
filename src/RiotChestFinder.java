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
                    if (i % 2 != 0) {
                        info[i/2] = response.substring(1, response.length() - 2);
                    }else
                        response = "";
                    i++;
                }

                number = reader.read();
            }

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

    /**
     * Will get the information about a champion
     * that a specific player plays
     *
     * @param summonerId
     * @param championName will use this to get championId for the URL
     * @return
     */
    public RiotChampion getRiotChampion(String summonerId, String championName) throws FileNotFoundException{
        for(RiotChampion champion: champions){
            if(champion.getChampionId().equals(getChampionIdFromFile(championName))){
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


        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Will use when making a new champion to get their id from the file
     * Otherwise if the champion already exists use RiotChampion.getChampionId();
     *
     * @param championName
     * @return
     * @throws FileNotFoundException
     */
    public static String getChampionIdFromFile(String championName) throws FileNotFoundException{
        String id = "";
        Scanner scan = new Scanner(new File("championIds.txt"));

        while(scan.hasNextLine()){
            String hold = scan.nextLine();
            if(hold.substring(0, hold.indexOf('=')).trim().equals(championName)) {
                id = hold.substring(hold.indexOf('=') + 2);
                return id;
            }
        }

        return id;
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
