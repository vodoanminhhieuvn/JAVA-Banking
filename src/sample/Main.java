package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Objects;

public class Main extends Application {
    static Stage window;
    static Scene scene, mainScene;
    static Parent mainRoot;
    static Parent cardLoginRoot;
    static Parent userLoginRoot;
    static double xOffset, yOffset;
    static Scene loginScene, cardScene;

    public static OkHttpClient client = new OkHttpClient();
    public JSONParser parseJSON = new JSONParser();
    public JSONObject jsonObject;
    public JSONObject res;
    public JSONArray messageJSON;
    public String resJSON;
    public String catchJsonObject;
    public static Request request;
    public static Response response;

    private static String tokenApi;
    // boolean checkInternetConnection = false;

    // TODO: Finishing up multiThreading

    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO: MultiTasking Network checking

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

        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("sample/resources/login/login.fxml")));
        scene = new Scene(root);

        mainRoot = FXMLLoader.load(Objects
                .requireNonNull(getClass().getClassLoader().getResource("sample/resources/mainActivity/Main.fxml")));
        cardLoginRoot = FXMLLoader.load(Objects
                .requireNonNull(getClass().getClassLoader().getResource("sample/resources/login/cardLogin.fxml")));
        userLoginRoot = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("sample/resources/login/login.fxml")));
        mainScene = new Scene(mainRoot);

        loginScene = new Scene(cardLoginRoot);
        cardScene = new Scene(userLoginRoot);

        // primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setTokenApi(String Token) {
        tokenApi = Token;
    }

    public static String getTokenApi() {
        return tokenApi;
    }

    public static void changeToMainView() {
        window.setTitle("Banking");
        mainScene.setFill(Color.TRANSPARENT);
        window.setScene(mainScene);
    }

    public static void changeToCardLoginView() {
        window.setTitle("Card Login");
        mainScene.setFill(Color.TRANSPARENT);

        window.setScene(loginScene);
    }

    public static void changeToUserLoginView() {
        window.setTitle("User Login");
        mainScene.setFill(Color.TRANSPARENT);

        window.setScene(cardScene);
    }

    public static Response requestAPI(JSONObject jsonObject, String Url) {

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        request = new Request.Builder().url(Url).post(body).build();

        try {
            response = client.newCall(request).execute();
            return response;
        } catch (Exception exception) {
            return null;
        }
    }

    public static Response requestAPI(JSONObject jsonObject, String Url, String TokenKey) {

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        request = new Request.Builder().url(Url).addHeader("auth-token", TokenKey).post(body).build();

        try {
            response = client.newCall(request).execute();
            return response;
        } catch (Exception exception) {
            return null;
        }
    }
}
