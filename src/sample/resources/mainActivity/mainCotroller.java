package sample.resources.mainActivity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainCotroller implements Initializable {

    @FXML
    Button registerViewBtn, createCardViewBtn, sendMoneyViewBtn;

    @FXML
    AnchorPane tabView;

    Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            root = FXMLLoader.load(getClass().getResource("register.fxml"));
            tabView.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // phoneInput.textProperty().addListener(new ChangeListener<phoneInputing>() {
        // @Override
        // public void changed(final ObservableValue<? extends phoneInputing> ov, final
        // phoneInputing
        // oldValue,
        // final phoneInputing newValue) {
        // if (phoneInput.getText().length() > 16) {
        // phoneInputing s = phoneInput.getText().subphoneInputing(0, 16);
        // phoneInput.setText(s);
        // }
        // }
        // });
    }

    @FXML
    public void changeAnchorView(MouseEvent event) {
        if (event.getSource() == registerViewBtn) {
            try {
                root = FXMLLoader.load(getClass().getResource("register.fxml"));
                tabView.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == createCardViewBtn) {
            try {
                root = FXMLLoader.load(getClass().getResource("createCard.fxml"));
                tabView.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == sendMoneyViewBtn) {
            try {
                root = FXMLLoader.load(getClass().getResource("sendMoney.fxml"));
                tabView.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
