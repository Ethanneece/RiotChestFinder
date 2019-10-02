import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class RiotChestFinder {

    private String developmentKey;
    private URL url;

    public RiotChestFinder() throws FileNotFoundException {

        Scanner reader = new Scanner("DevelopmentKey");

        if(reader.ioException() != null) {
            throw new FileNotFoundException("DevelopmentKey Could not be found");
        }

        developmentKey = reader.nextLine();

        url = new URL()
    }
}
