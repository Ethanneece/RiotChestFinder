import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestAllison {
    public static void main(String[] args) throws FileNotFoundException {
        RiotChestFinder action = new RiotChestFinder();
        RiotID summoner = action.getRiotID("CascadingRayn");
        RiotChampion summonerChamp = action.getRiotChampion(summoner.getSummonerId(), "Lulu");
        System.out.println(summoner);
        System.out.println(summonerChamp);
    }

}
