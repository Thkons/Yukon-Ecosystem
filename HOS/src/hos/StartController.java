/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hos;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Thanasis
 */
public class StartController implements Initializable {
@FXML 
private Button button_login1;

@FXML 
private Button button_login2;

@FXML 
private Button button_login3;

@FXML 
private Button button_login4;


@FXML 
private Button button_sign_up;


    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         
       

        
                  button_login1.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "medical_secretary.fxml", "Login As Medical Secretary",  null, null);
          }
      });
                 
                 
                 button_login2.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "doctor.fxml", "Login As Doctor",  null, null);
          }
      });
                 
                 button_login3.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "support_team.fxml", "Login As A Support Team Member",  null, null);
          }
      });
                 
                  button_login4.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "patient.fxml", "Login As A Patient",  null, null);
          }
      });
                  
                   
                 
                  button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "register_application.fxml", "Register Application", null, null);
          }
      });
    }
}
