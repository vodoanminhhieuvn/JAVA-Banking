package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainCotroller implements Initializable {

    @FXML
    AnchorPane tabView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("resources/mainActivity/register.fxml"));
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

    }

}
