import java.util.Objects;

public class Summoner {
    private String summonerName;
    private String summonerId;
    private String accountId;

    public static final String ID_REQUEST = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    public static final String CHAMPION_ID_REQUEST = "https://na1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/";


    public Summoner(String summonerName, String summonerId, String accountId) {
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
                "summonerName = '" + summonerName + '\'' +
                ", summonerId = '" + summonerId + '\'' +
                ", accountId = '" + accountId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summoner summoner = (Summoner) o;
        return Objects.equals(summonerName, summoner.summonerName) &&
                Objects.equals(summonerId, summoner.summonerId) &&
                Objects.equals(accountId, summoner.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summonerName, summonerId, accountId);
    }
}
