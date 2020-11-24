package sample.resources.mainActivity;

import sample.AlertBox;
import sample.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import okhttp3.Response;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class sendMoneyController implements Initializable {
    @FXML
    private JFXTextField IDInput, fundInput;

    @FXML
    private JFXButton sendMoneyButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IDInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (IDInput.getText().length() > 16) {
                AlertBox.display("Card ID Limit", "Card ID limit: 16");
                IDInput.setText(IDInput.getText().substring(0, IDInput.getText().length() - 1));
            }

            if (!IDInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid card ID", "Card ID does not contain words");
                IDInput.setText(IDInput.getText().substring(0, IDInput.getText().length() - 1));
            }
        });

        fundInput.textProperty().addListener((arg, oldVal, newVal) -> {
            if (!fundInput.getText().matches("[0-9]*")) {
                AlertBox.display("Invalid Fund", "Fund does not contain words");
                fundInput.setText(fundInput.getText().substring(0, fundInput.getText().length() - 1));
            }
        });
    }

    @FXML
    public void sendMoney() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cardId", IDInput.getText());
            jsonObject.put("fund", fundInput.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String Url = "http://localhost:9000/api/send-money";
        String strJSon;
        Long catchJsonObject;

        try {
            Response response = Main.requestAPI(jsonObject, Url, Main.getTokenApi());
            strJSon = response.body().string();

            try {
                JSONParser parseJson = new JSONParser();
                JSONObject jsonMap = (JSONObject) parseJson.parse(strJSon);
                catchJsonObject = (Long) jsonMap.get("cardSend_balance");
                System.out.println("Hello " + catchJsonObject);
                AlertBox.display("Transfer Successfully", "Your balance " + catchJsonObject.toString());
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
}
