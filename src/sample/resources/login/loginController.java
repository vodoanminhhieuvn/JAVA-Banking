package sample.resources.login;

import sample.Main;
import sample.AlertBox;

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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private JFXTextField inputEmail, inputName;

    @FXML
    private JFXPasswordField inputPassword;

    @FXML
    private JFXButton btnSignIn, btnClose, btnSignUpAccount;

    @FXML
    private ImageView btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
    public void handleViewChange(MouseEvent event) {
        Main.changeToCardLoginView();
    }

    public void Login() throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", inputEmail.getText());
            jsonObject.put("password", inputPassword.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String Url = "http://localhost:9000/api/user/login";
        String strJSon;
        String catchJsonObject;

        try {
            Response response = Main.requestAPI(jsonObject, Url);
            strJSon = response.body().string();

            try {
                JSONParser parseJson = new JSONParser();
                JSONObject jsonMap = (JSONObject) parseJson.parse(strJSon);
                catchJsonObject = (String) jsonMap.get("Token");
                System.out.println("Hello " + catchJsonObject);
                Main.setTokenApi(catchJsonObject);
                Main.changeToMainView();
            } catch (Exception cause) {
                System.err.println(strJSon);
                AlertBox.display("Alert", strJSon);
            }

        } catch (Exception cause) {
            System.out.println(cause);
            // AlertBox.display("Alert", strJSon);
        }
    }

}
