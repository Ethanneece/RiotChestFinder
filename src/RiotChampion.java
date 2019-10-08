import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class RiotChampion {

    private String championId;
    private String championName;
    private long lastPlayTime;
    private boolean hasChest;

    public static final String ID_REQUEST = "https://na1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/";

    public RiotChampion(String championId, String championName, long lastPlayTime, boolean hasChest){
        this.championId = championId;
        this.championName = championName;
        this.lastPlayTime = lastPlayTime;
        this.hasChest = hasChest;
    }

    public String getChampionId() {
        return championId;
    }

    public String getChampionName() {
        return championName;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime){
        this.lastPlayTime = lastPlayTime;
    }

    public boolean isHasChest(){
        return hasChest;
    }

    /**
     * Will use when making a new champion to get their id from the file
     * Otherwise if the champion already exists use RiotChampion.getChampionId();
     *
     * @param championName
     * @return
     * @throws FileNotFoundException
     */
    public static int getChampionIdFromFile(String championName) throws FileNotFoundException {
        String id = "";
        Scanner scan = new Scanner(new File("championIds.txt"));

        while(scan.hasNextLine()) {
            String hold = scan.nextLine();

            if(hold.contains(championName)) {
                return new Integer(hold.substring(hold.indexOf('=') + 2));
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return "RiotChampion{" +
                "championId = '" + championId + '\'' +
                ", championName = '" + championName + '\'' +
                ", lastPlayTime = " + lastPlayTime +
                ", hasChest= " + hasChest +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiotChampion that = (RiotChampion) o;
        return Objects.equals(championId, that.championId) &&
                Objects.equals(championName, that.championName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(championId, championName);
    }
}
