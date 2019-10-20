import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Summoners {
    private ArrayList<Summoner> summoners = new ArrayList<Summoner>();

    //method to load favorites
    public Summoners() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("favoriteSummoners.txt"));
        while(scan.hasNextLine()){

        }
    }

    public void addSummoner(Summoner searched){
        if(!summoners.contains(searched))   //double check this bc it might just check they point to same thing
            summoners.add(searched);
    }

    //method for favoritin
    public void favorite(Summoner faved) {
        if (!summoners.contains(faved)){
            addSummoner(faved);
        return;
        }
        summoners.get(summoners.indexOf(faved)).setFavorited(true);
    }

    //method for saving favorites into file
}
