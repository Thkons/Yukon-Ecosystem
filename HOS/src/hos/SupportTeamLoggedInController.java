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
import javafx.scene.control.Label;

/**
 *
 * @author Thanasis
 */
public class SupportTeamLoggedInController implements Initializable {
     private String username;
    private String ISA;
@FXML 
private Button button_logout;
@FXML 
private Label label_welcome;
@FXML 
private Button button_feedback;
@FXML 
private Button button_reports;
private int loggedInSupportTeamId;


public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
        label_welcome.setText("WELCOME TO HOS "+  username);
    }
  public void setLoggedInSupportTeamId(int loggedInSupportTeamId) {
        this.loggedInSupportTeamId = loggedInSupportTeamId;
        System.out.println("Logged-in Support Team Member ID set to: " + loggedInSupportTeamId); // Debugging
    }
  
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         
       

        
                  button_feedback.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene6(event, "feedBackList.fxml", "Feed Back List",  username, ISA, loggedInSupportTeamId);
          }
      });
                 
                 
                  button_reports.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene6(event, "reports_list.fxml", "Reports List", username, ISA, loggedInSupportTeamId);
          }
      });
             

           
             
          
      button_logout.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
          }
      });
         
          

            
           
         
    }
  
}
