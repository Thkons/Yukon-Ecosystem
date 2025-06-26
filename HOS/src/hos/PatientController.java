package hos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private Button button_login;
    @FXML
    private Button button_sign_up;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_goBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_login.setOnAction(event -> {
            DBUtils.logInUser4(event, tf_username.getText(), tf_password.getText());
        });

        button_sign_up.setOnAction(event -> {
            DBUtils.changeScene0(event, "register_application.fxml", "Register Application", null, null);
        });

        button_goBack.setOnAction(event -> {
            DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
        });
    }
}
