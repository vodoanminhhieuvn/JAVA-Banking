package sample.resources.login;

import sample.AlertBox;
import sample.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import okhttp3.Response;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class cardLoginController implements Initializable {

    @FXML
    private JFXButton changeViewButton;

    @FXML
    private JFXTextField cardIDInput;

    @FXML
    private JFXPasswordField PINInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardIDInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (cardIDInput.getText().length() > 16) {
                AlertBox.display("Card ID Limit", "Card ID limit: 16");
                cardIDInput.setText(cardIDInput.getText().substring(0, cardIDInput.getText().length() - 1));
            }

            if (!cardIDInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid card ID", "Card ID does not contain words");
                cardIDInput.setText(cardIDInput.getText().substring(0, cardIDInput.getText().length() - 1));
            }
        });

        PINInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (PINInput.getText().length() > 6) {
                AlertBox.display("PIN Limit", "PIN limit: 6");
                PINInput.setText(PINInput.getText().substring(0, PINInput.getText().length() - 1));
            }

            if (!PINInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid PIN", "PIN does not contain words");
                PINInput.setText(PINInput.getText().substring(0, PINInput.getText().length() - 1));
            }
        });
    }

    @FXML
    public void logIn(MouseEvent event) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cardId", cardIDInput.getText());
            jsonObject.put("PIN", PINInput.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String Url = "http://localhost:9000/api/user/card-login";
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

    @FXML
    public void handleViewChange(MouseEvent event) {
        Main.changeToUserLoginView();
    }

}
