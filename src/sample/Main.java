package sample;

import java.io.IOException;
import java.lang.Object;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    static Stage window;
    static Scene scene, mainScene;
    static Parent mainRoot;
    static double xOffset, yOffset;
    boolean checkInternetConnection = false;

    // TODO: Finishing up multiThreading

    // HttpURLConnection connection;
    // Task task2;

    // public void callNetwork() throws IOException {

    // System.out.println("Good999999999999999999999999999999");
    // try {
    // URL url = new URL("http://www.google.com");
    // connection = (HttpURLConnection) url.openConnection();
    // connection.connect();
    // System.out.println(connection.getInputStream());

    // } catch (Exception e) {
    // connection.disconnect();
    // System.out.println("Disconnected");

    // }
    // }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO: MultiTasking

        // task = new Task<Void>() {
        // @Override
        // public Void call() throws IOException {
        // while (true) {
        // callNetwork();
        // }
        // }
        // };

        // ProgressBar bar = new ProgressBar();
        // bar.progressProperty().bind(task.progressProperty());
        // new Thread(task).start();

        // Platform.runLater(() -> {
        // try {
        // URL url = new URL("http://www.google.com");
        // URLConnection connection = url.openConnection();
        // connection.connect();
        // System.out.println("Internet is connected");
        // } catch (Exception e) {
        // System.out.println("You don't have internet connection");
        // }
        // });

        window = primaryStage;

        Platform.setImplicitExit(false);

        Parent root = FXMLLoader.load(getClass().getResource("resources/login.fxml"));
        scene = new Scene(root);

        mainRoot = FXMLLoader.load(getClass().getResource("resources/Main.fxml"));
        mainScene = new Scene(mainRoot);

        dragView(root, primaryStage);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void dragView(Parent root, Stage primaryStage) {
        root.setOnMousePressed(e -> {
            xOffset = primaryStage.getX() - e.getScreenX();
            yOffset = primaryStage.getY() - e.getScreenY();
        });

        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() + xOffset);
            primaryStage.setY(e.getScreenY() + yOffset);
        });
    }

    public static void changeToMainView() {
        dragView(mainRoot, window);
        mainScene.setFill(Color.TRANSPARENT);
        window.setScene(mainScene);

    }
}
