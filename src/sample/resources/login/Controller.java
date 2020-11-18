package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.AlertBox;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private OkHttpClient client = new OkHttpClient();
    private JSONParser parseJSON = new JSONParser();
    private JSONObject jsonObject;
    private JSONObject res;
    private JSONArray messageJSON;
    private String resJSON;
    private String catchJsonObject;
    private Request request;
    private Response response;

    @FXML
    private JFXTextField inputEmail, inputName;

    @FXML
    private JFXPasswordField inputPassword;

    @FXML
    private JFXButton btnSignIn, btnClose, btnSignUpAccount;

    @FXML
    private ImageView btnBack;

    @FXML
    public void handleSignIn(MouseEvent event) throws IOException {
        Login();
    }

    @FXML
    public void loginEnterClick(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Login();
        }
    }

    @FXML
    public void handleViewChange(MouseEvent event) throws IOException {
    }

    @FXML
    public void buttonHandleEvent(MouseEvent event) {
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Login() throws IOException {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", inputEmail.getText());
            jsonObject.put("password", inputPassword.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        request = new Request.Builder().url("http://localhost:9000/api/user/login").post(body).build();

        // ? Check internet connection
        try {
            response = client.newCall(request).execute();
        } catch (Exception exception) {
            Main.changeToMainView();
            AlertBox.display("Connection problem", "Please check your internet connection");
            return;
        }

        // ! @param response can be only used for one time only

        // ? Login
        try {
            // ! After this response will be deleted
            // ! JSONArray must be array when parsing
            // ! When we receive only one object we should use String
            // ! When we receive list of objects we should jsonArray

            resJSON = response.body().string();
            try {
                res = (JSONObject) parseJSON.parse(resJSON);
                catchJsonObject = (String) res.get("Token");
                System.out.println("Hello " + catchJsonObject);
                Main.changeToMainView();
            } catch (Exception e) {
                System.out.println(resJSON);
                AlertBox.display("Alert", resJSON);

            }

            // System.out.println(res);
            // System.out.println(res.get("success"));
            // System.out.println("--------------------------------");
            // System.out.println(messageJSON);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
