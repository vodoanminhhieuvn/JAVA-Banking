package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private JFXTextField inputEmail;

    @FXML
    private JFXPasswordField inputPassword;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private AnchorPane loginPane;

    @FXML
    public void handleSignIn(MouseEvent event) {
        Login();
    }

    @FXML
    public void loginEnterClick(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Login();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void Login() {
        System.out.println(inputEmail.getText());
        System.out.println(inputPassword.getText());
    }

}
