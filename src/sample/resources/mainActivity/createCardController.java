package sample.resources.mainActivity;

import com.jfoenix.controls.JFXButton;
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

public class createCardController implements Initializable {

    @FXML
    JFXTextField IDInput, PINInput, emailInput, balanceInput;

    @FXML
    JFXButton createCardBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IDInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (IDInput.getText().length() > 16) {
                AlertBox.display("Phone number Limit", "ID number limit: 16");
                IDInput.setText(IDInput.getText().substring(0, IDInput.getText().length() - 1));
            }

            if (!IDInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid phone number", "ID number does not contain words");
                IDInput.setText(IDInput.getText().substring(0, IDInput.getText().length() - 1));
            }
        });

        PINInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (PINInput.getText().length() > 6) {
                AlertBox.display("Phone number Limit", "PIN number limit: 6");
                PINInput.setText(PINInput.getText().substring(0, PINInput.getText().length() - 1));
            }

            if (!PINInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid phone number", "PIN number does not contain words");
                PINInput.setText(PINInput.getText().substring(0, PINInput.getText().length() - 1));
            }
        });
    }

    private void createCard() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", IDInput.getText());
            jsonObject.put("PIN", PINInput.getText());
            jsonObject.put("email", emailInput.getText());
            jsonObject.put("balance", balanceInput.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String Url = "http://localhost:9000/api/card-create";
        String strJSon;
        Long catchJsonObject;

        try {
            Response response = Main.requestAPI(jsonObject, Url, Main.getTokenApi());
            strJSon = response.body().string();

            try {
                JSONParser parseJson = new JSONParser();
                JSONObject jsonMap = (JSONObject) parseJson.parse(strJSon);
                catchJsonObject = (Long) jsonMap.get("cardId");
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
        if (event.getSource() == createCardBtn) {
            createCard();
        }
    }

}
