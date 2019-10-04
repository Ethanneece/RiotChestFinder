import java.util.Objects;

public class RiotChampion {

    private int championId;
    private String championName;
    private long lastPlayTime;

    public static final String ID_REQUEST = "https://na1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/";

    public RiotChampion(int championID, String championName){
        this.championId = championID;
        this.championName = championName;
    }

    public int getChampionId() {
        return championId;
    }

    public String getChampionName() {
        return championName;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    @Override
    public String toString() {
        return "RiotChampion{" +
                "championID='" + championId + '\'' +
                ", championName='" + championName + '\'' +
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
