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
public class thankYouController implements Initializable{
    
    @FXML 
    private Button button_goBack;
    @FXML 
    private Label thanks;
    
    
     @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        button_goBack.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
              DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
          }
          });
        
    }
    public void setUserInformation(String username, String ISA)
    {
            
           
        thanks.setText("Thank You " +  username +" For Your Application");
    
    }
}
