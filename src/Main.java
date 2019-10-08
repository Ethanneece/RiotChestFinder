import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main extends Application {

    private final int WIDTH = 500;
    private final int HEIGHT = 600;

    private Button summonerController;
    private TextField summonerInput;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("League of Legends Info");
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);

        BorderPane root = new BorderPane();

        summonerInput = new TextField();
        summonerController = new Button("Find Summoner");
        HBox summoner = new HBox(summonerInput, summonerController);
        summoner.setAlignment(Pos.CENTER);
        root.setBottom(summoner);
        root.setAlignment(summoner, Pos.BASELINE_CENTER);

        summonerController.setAlignment(Pos.CENTER);

        summonerController.setOnMouseClicked( e -> {
            try {
                RiotChestFinder find = new RiotChestFinder();
                RiotID summonerId = find.getRiotID(summonerInput.getText());
//                System.out.println(summonerId);

                Text name = new Text("NAME: " + summonerId.getSummonerName());
                Text id = new Text("SUMMONER ID: " + summonerId.getSummonerId());
                Text account = new Text("ACCOUNT ID: " + summonerId.getAccountId());
                VBox info = new VBox(name, id, account);
                root.setCenter(info);

            }catch (FileNotFoundException b){
                System.out.println("File not found");
            }
        });

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void buttonPress() {
        
    }
}
