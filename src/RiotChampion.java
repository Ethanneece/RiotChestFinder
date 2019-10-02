import java.util.Objects;

public class RiotChampion {

    private String championID;
    private String championName;

    public RiotChampion(String championID, String championName){
        this.championID = championID;
        this.championName = championName;
    }

    public String getChampionID() {
        return championID;
    }

    public String getChampionName() {
        return championName;
    }

    @Override
    public String toString() {
        return "RiotChampion{" +
                "championID='" + championID + '\'' +
                ", championName='" + championName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiotChampion that = (RiotChampion) o;
        return Objects.equals(championID, that.championID) &&
                Objects.equals(championName, that.championName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(championID, championName);
    }
}
