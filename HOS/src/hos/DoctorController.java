/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Thanasis
 */
public class DoctorController implements Initializable {
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
        button_login.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        
        DBUtils.logInUser2(event, tf_username.getText(), tf_password.getText());
    }
});

         button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "register_application.fxml", "Register Application", null, null);
          }
      });
         button_goBack.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
          }
      });
    }

    
}