package sample.resources.mainActivity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sample.AlertBox;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class createCardController implements Initializable {

    @FXML
    JFXTextField IDInput, PINInput, emailInput, balanceInput;

    @FXML
    JFXButton createCardBtn;

    private Main main = new Main();
    private Boolean emailNeeded = false;

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
        main.jsonObject = new JSONObject();
        try {
            main.jsonObject.put("_id", IDInput.getText());
            main.jsonObject.put("PIN", PINInput.getText());
            main.jsonObject.put("email", emailInput.getText());
            main.jsonObject.put("balance", balanceInput.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, main.jsonObject.toString());
        main.request = new Request.Builder().url("http://localhost:9000/api/user/card-create").post(body).build();

        // ? Check internet connection
        // ! Create in main function
        try {
            main.response = main.client.newCall(main.request).execute();
        } catch (Exception exception) {
            Main.changeToMainView();
            AlertBox.display("Connection problem", "Please check your internet connection");
            return;
        }

        // ! @param response can be only used for one time only

        // ? Create Card
        try {
            // ! After this response will be deleted
            // ! JSONArray must be array when parsing
            // ! When we receive only one object we should use string
            // ! When we receive list of objects we should jsonArray

            main.resJSON = main.response.body().string();
            try {
                main.res = (JSONObject) main.parseJSON.parse(main.resJSON);
                // main.messageJSON = (JSONArray) main.res.get("error");
                main.catchJsonObject = (String) main.res.get("cardId");
                System.out.println(main.resJSON);
                AlertBox.display("Alert", main.catchJsonObject);
            } catch (Exception e) {
                System.out.println(main.resJSON);
                if (main.resJSON.equals("User is not exist")) {
                    emailNeeded = true;
                    System.out.println(emailNeeded);
                }
                AlertBox.display("Alert", main.resJSON);
            }

            // System.out.println(res);
            // System.out.println(res.get("success"));
            // System.out.println("--------------------------------");
            // System.out.println(messageJSON);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == createCardBtn) {
            createCard();
        }
    }

}
