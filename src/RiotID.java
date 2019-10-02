import java.net.URL;
import java.util.Objects;

public class RiotID {

    private String summonerName;
    private String summonerId;
    private String accountId;

    public RiotID(String summonerName) {

        URL gettingsIDs = new URL("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + );
    }

    public RiotID(String summonerName, String summonerId, String accountId) {
        this.summonerName = summonerName;
        this.summonerId = summonerId;
        this.accountId = accountId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "RiotID{" +
                "summonerName='" + summonerName + '\'' +
                ", summonerId='" + summonerId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiotID riotID = (RiotID) o;
        return Objects.equals(summonerName, riotID.summonerName) &&
                Objects.equals(summonerId, riotID.summonerId) &&
                Objects.equals(accountId, riotID.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summonerName, summonerId, accountId);
    }
}
