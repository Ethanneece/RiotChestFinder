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
    private RiotChestFinder finder;

    @Override
    public void start(Stage primaryStage) throws Exception {

        finder = new RiotChestFinder();

        primaryStage.setTitle("FindingChestChampions");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        BorderPane root = new BorderPane();

        summonerInput = new TextField();
        summonerController = new Button("Find Summoner");
        HBox summoner = new HBox(summonerInput, summonerController);
        summoner.setAlignment(Pos.CENTER);
        root.setBottom(summoner);
        BorderPane.setAlignment(summoner, Pos.BASELINE_CENTER);

        summonerController.setAlignment(Pos.CENTER);

        summonerController.setOnMousePressed(e -> summonerControllerPress());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void summonerControllerPress() {
        Summoner player = finder.getSummoner(summonerInput.getText());

        summonerInput.clear();

        if(player != null) {
            summonerInput.setText(player.getSummonerId());
        }
        else {
            summonerInput.setText("Invalid Request");
        }
    }
}
