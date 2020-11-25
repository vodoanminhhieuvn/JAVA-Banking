package sample.resources.mainActivity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import okhttp3.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sample.AlertBox;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    @FXML
    JFXTextField nameInput, emailInput, phoneInput;

    @FXML
    JFXPasswordField passwordInput, confirmInput;

    @FXML
    JFXButton signUpBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (phoneInput.getText().length() > 16) {
                AlertBox.display("Phone number Limit", "Phone number limit: 16");
                phoneInput.setText(phoneInput.getText().substring(0, phoneInput.getText().length() - 1));
            }

            if (!phoneInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid phone number", "Phone number does not contain words");
                phoneInput.setText(phoneInput.getText().substring(0, phoneInput.getText().length() - 1));
            }
        });
    }

    private void SignUp() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", nameInput.getText());
            jsonObject.put("email", emailInput.getText());
            jsonObject.put("phone", phoneInput.getText());
            jsonObject.put("password1", passwordInput.getText());
            jsonObject.put("password2", confirmInput.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String Url = "http://localhost:9000/api/user/register";
        String strJSon;
        String catchJsonObject;

        try {
            Response response = Main.requestAPI(jsonObject, Url, Main.getTokenApi());
            strJSon = response.body().string();

            try {
                JSONParser parseJson = new JSONParser();
                JSONObject jsonMap = (JSONObject) parseJson.parse(strJSon);
                catchJsonObject = (String) jsonMap.get("user");
                System.out.println("Hello " + catchJsonObject);
            } catch (Exception cause) {
                System.out.println(cause);
                System.err.println(strJSon);
                AlertBox.display("Alert", strJSon);
            }

        } catch (Exception cause) {
            System.out.println(cause);
            // AlertBox.display("Alert", strJSon);
        }
    }

    @FXML
    public void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == signUpBtn) {
            SignUp();
        }
    }

}
