import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main extends Application {

    private final int WIDTH = 500;
    private final int HEIGHT = 600;

    private Button summonerController;
    private RiotChestFinder finder;
    private TextField summonerInput;
    private VBox io;
    private Scene scene;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        finder = new RiotChestFinder();

        primaryStage.setTitle("FindingChestChampions");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        root = new BorderPane();

        summonerInput = new TextField();
        HBox tagAndInput = new HBox(new Text("Name: "), summonerInput);
        tagAndInput.setAlignment(Pos.CENTER);
        summonerController = new Button("Find Summoner");

        VBox summoner = new VBox(tagAndInput, summonerController);
        summoner.setSpacing(2);
        summoner.setAlignment(Pos.CENTER);

        io = new VBox(summoner);
        root.setCenter(io);
        io.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(summoner, Pos.BOTTOM_CENTER);

        summonerController.setAlignment(Pos.CENTER);

        summonerController.setOnMousePressed(e -> summonerControllerPress());

        scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void summonerControllerPress() {
        Summoner player = finder.getSummoner(summonerInput.getText());

        player.setChampionsWithOutChest(finder.getNoChests(player.getSummonerId()));

        summonerInput.clear();

        summonerInput.setText(player.getRandomChamp());

        if(player != null) {
            Text info = new Text(player.getSummonerId());
            info.wrappingWidthProperty().bind(scene.widthProperty().subtract(10));
            info.setTextAlignment(TextAlignment.CENTER);
            io.getChildren().add(info);
        }
        else {
            summonerInput.setText("Invalid Request");
        }
    }
}
