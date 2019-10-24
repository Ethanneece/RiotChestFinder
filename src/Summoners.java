import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Summoners {
    private ArrayList<Summoner> summoners = new ArrayList<Summoner>();
    private final File REFERENCE = new File("favoriteSummoners.txt");

    //method to load favorites
    public Summoners() throws IOException{
        REFERENCE.createNewFile();
        Scanner scan = new Scanner(REFERENCE);
        while (scan.hasNextLine()) {
            Summoner s = new Summoner(scan.next(), scan.next(), scan.next(), scan.nextBoolean());
            summoners.add(s);
        }
    }

    public Summoner getSummoner(Summoner s){
        if(summoners.contains(s))
            return summoners.get(summoners.indexOf(s));
        return s;
    }

    public void addSummoner(Summoner searched){
        if(!summoners.contains(searched))
            summoners.add(searched);
    }

    //method for favoritin
    public void changeFavoriteStatus(Summoner faved) {
        if (!summoners.contains(faved)){
            addSummoner(faved);
        return;
        }
        summoners.get(summoners.indexOf(faved)).switchFavorite();
        //maybe resort the list
    }

    //method for saving favorites into file
    public void saving(){

    }
}
