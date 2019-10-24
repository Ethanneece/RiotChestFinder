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

    private RiotChestFinder finder;
    private Summoners players;

    private Button summonerController;
    private Button summonerFavorite;
    private TextField summonerInput;
    private VBox io;
    private Scene scene;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        players = new Summoners();
        finder = new RiotChestFinder();

        primaryStage.setTitle("FindingChestChampions");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        root = new BorderPane();

        summonerInput = new TextField();
        HBox tagAndInput = new HBox(new Text("Name: "), summonerInput);
        tagAndInput.setAlignment(Pos.CENTER);

        summonerController = new Button("Find Summoner");
        summonerController.setAlignment(Pos.CENTER);
        summonerController.setOnMousePressed(e -> summonerControllerPress());

        summonerFavorite = new Button("☆");
        summonerFavorite.setAlignment(Pos.CENTER);
        summonerFavorite.setOnMousePressed(e -> favoriting());

        VBox summoner = new VBox(tagAndInput, summonerController, summonerFavorite);
        summoner.setSpacing(2);
        summoner.setAlignment(Pos.CENTER);

        io = new VBox(summoner);
        root.setCenter(io);
        io.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(summoner, Pos.BOTTOM_CENTER);

        scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void summonerControllerPress() {
        Summoner player = finder.getSummoner(summonerInput.getText());

        player.setChampionsWithOutChest(finder.getNoChests(player.getSummonerId()));

//        summonerInput.clear();

        if(player != null) {
            if(players.getSummoner(player).getFavorite())
                summonerFavorite.setText("★");
            else
                summonerFavorite.setText("☆");
            Text info = new Text(player.getSummonerId() + "\nRandom Champ: " + player.getRandomChamp());
            info.wrappingWidthProperty().bind(scene.widthProperty().subtract(10));
            info.setTextAlignment(TextAlignment.CENTER);
            io.getChildren().add(info);
        }
        else {
            summonerInput.clear();
            summonerInput.setText("Invalid Request");
        }

        players.addSummoner(player);
    }

    private void favoriting(){
        Summoner faved = finder.getSummoner(summonerInput.getText());
        if(faved != null) {
            if (!faved.getFavorite())
                summonerFavorite.setText("★");
            else
                summonerFavorite.setText("☆");
            players.changeFavoriteStatus(faved);
        }else{
            summonerInput.clear();
            summonerInput.setText("Invalid Request");
        }
    }
}
