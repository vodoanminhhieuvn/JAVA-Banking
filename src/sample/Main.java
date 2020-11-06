package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    Scene scene;
    double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("resources/login.fxml"));
        scene = new Scene(root);

        root.setOnMousePressed(e -> {
            xOffset = primaryStage.getX() - e.getScreenX();
            yOffset = primaryStage.getY() - e.getScreenY();
        });

        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() + xOffset);
            primaryStage.setY(e.getScreenY() + yOffset);
        });

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
