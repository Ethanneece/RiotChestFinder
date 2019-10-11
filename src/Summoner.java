import java.util.Objects;

public class Summoner {
    private String summonerName;
    private String summonerId;
    private String accountId;
    private String[] championsWithOutChest;

    public static final String ACCOUNT_ID_REQUEST = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    public static final String CHAMPIONS_REQUEST = "theres another differnt URL for if you want all champs I'll add it when the website isnt down";


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
        return "Summoner: " + summonerName + "\n" +
                "\t summonerId: " + summonerId + "\n" +
                "\t accountId: " + accountId + "\n";
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
