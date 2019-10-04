import java.io.FileNotFoundException;

public class TestAllison {
    public static void main(String[] args) throws FileNotFoundException {
        RiotChestFinder action = new RiotChestFinder();
        RiotID gotoe11 = action.getRiotID("gotoe11");
        System.out.println(action.getChampionLastPlayTime(gotoe11.getSummonerId(), "1"));
    }
}
