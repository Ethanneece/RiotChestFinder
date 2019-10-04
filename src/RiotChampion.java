import java.util.Objects;

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
