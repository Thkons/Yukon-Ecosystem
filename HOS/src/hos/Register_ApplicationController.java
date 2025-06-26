package hos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Register_ApplicationController implements Initializable {

    @FXML
    private Button button_signup;
    
    @FXML
    private Button button_log_in;
    
    @FXML
    private TextField tf_username;
     
    @FXML
    private TextField tf_password;
      
    @FXML
    private TextField tf_r_password;

    @FXML
    private RadioButton rb_patient;
      
    @FXML
    private RadioButton rb_doctor;
      
    @FXML
    private RadioButton rb_support_team;
      
    @FXML
    private RadioButton rb_medicalSecretary;
      
    @FXML
    private Button button_goBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_doctor.setToggleGroup(toggleGroup);
        rb_medicalSecretary.setToggleGroup(toggleGroup);
        rb_support_team.setToggleGroup(toggleGroup);
        rb_patient.setToggleGroup(toggleGroup);
   
        rb_patient.setSelected(true);
        
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();    

                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_r_password.getText().trim().isEmpty()) {
                    
                    DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), tf_r_password.getText(), toggleName);
                    // After user is registered and a patient is created
                    

                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information");
                    alert.show();
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
            }
        });

        button_goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
            }
        });
    }
}
