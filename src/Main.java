import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    private final int WIDTH = 500;
    private final int HEIGHT = 600;

    private Button controller;



    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("SomethingSomething");
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);

        BorderPane root = new BorderPane();

        controller = new Button("Hello");
        root.setBottom(controller);
        root.setAlignment(controller, Pos.BASELINE_CENTER);

        controller.setAlignment(Pos.CENTER);

        controller.setOnMouseClicked( e -> buttonPress());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void buttonPress() {
        
    }
}
