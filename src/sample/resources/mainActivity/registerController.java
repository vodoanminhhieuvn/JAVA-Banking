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
import org.json.simple.JSONObject;
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

    private Main main = new Main();

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
        main.jsonObject = new JSONObject();
        try {
            main.jsonObject.put("name", nameInput.getText());
            main.jsonObject.put("email", emailInput.getText());
            main.jsonObject.put("phone", phoneInput.getText());
            main.jsonObject.put("password1", passwordInput.getText());
            main.jsonObject.put("password2", confirmInput.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, main.jsonObject.toString());
        main.request = new Request.Builder().url("http://localhost:9000/api/user/register").post(body).build();

        // ? Check internet connection
        try {
            main.response = main.client.newCall(main.request).execute();
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
            // ! When we receive only one object we should use phoneInputing
            // ! When we receive list of objects we should jsonArray

            main.resJSON = main.response.body().string();
            try {
                main.res = (JSONObject) main.parseJSON.parse(main.resJSON);
                main.catchJsonObject = (String) main.res.get("user");
                System.out.println("Hello " + main.catchJsonObject);
                AlertBox.display("Alert", main.catchJsonObject);
            } catch (Exception e) {
                System.out.println(main.resJSON);
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
        if (event.getSource() == signUpBtn) {
            SignUp();
        }
    }

}
