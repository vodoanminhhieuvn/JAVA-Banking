package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Objects;

public class Main extends Application {
    static Stage window;
    static Scene scene, mainScene;
    static Parent mainRoot;
    static double xOffset, yOffset;

    public OkHttpClient client = new OkHttpClient();
    public JSONParser parseJSON = new JSONParser();
    public JSONObject jsonObject;
    public JSONObject res;
    public JSONArray messageJSON;
    public String resJSON;
    public String catchJsonObject;
    public Request request;
    public Response response;
    // boolean checkInternetConnection = false;

    // TODO: Finishing up multiThreading

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

        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/resources/login/login.fxml")));
        scene = new Scene(root);

        mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/resources/mainActivity/Main.fxml")));
        mainScene = new Scene(mainRoot);

        dragView(root, primaryStage);

        // primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Login");
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
        window.setTitle("Banking");
        mainScene.setFill(Color.TRANSPARENT);
        window.setScene(mainScene);
    }
}
