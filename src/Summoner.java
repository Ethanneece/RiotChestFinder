import java.util.ArrayList;
import java.util.Objects;

public class Summoner {

    private String summonerName, summonerId, accountId;
    private boolean favorite;
    private long[] noChestChampions;


    public static final String ACCOUNT_ID_REQUEST = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    public static final String CHAMPIONS_REQUEST = "https://na1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/";


    public Summoner(String summonerName, String summonerId, String accountId, boolean favorite) {
        this.summonerName = summonerName;
        this.summonerId = summonerId;
        this.accountId = accountId;
        this.favorite = favorite;

        noChestChampions = new ArrayList<>();
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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

    public boolean getFavorite() {
        return favorite; 
    }


    public String getRandomChamp() {
        return noChestChampions.get((int) (Math.random() * noChestChampions.size()));
    }

    public void setChampionsWithOutChest(ArrayList championsWithOutChest) {
        this.noChestChampions = championsWithOutChest;
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
