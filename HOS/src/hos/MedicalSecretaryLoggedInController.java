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
public class MedicalSecretaryLoggedInController implements Initializable {
     private String username;
    private String ISA;
@FXML 
private Button button_logout;
@FXML 
private Label label_welcome;
@FXML 
private Button button_doctors;
@FXML 
private Button button_patients;
@FXML 
private Button button_appointments_list;
@FXML 
private Button button_appointments_record;
@FXML 
private Button button_time_manage;
@FXML 
private Button button_users;

    
   public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
        label_welcome.setText("WELCOME TO HOS "+  username);
    } 
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         
       

        
                  button_doctors.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene1(event, "doctors_list.fxml", "Doctors List",  username, ISA);
          }
      });
                 
                 
                  button_patients.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene1(event, "patients_list.fxml", "Patients List", username, ISA);
          }
      });
             

           
             
          
      button_logout.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
          }
      });
         
          
     
            
            button_users.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene1(event, "users_list.fxml", "Users List", username, ISA);
          }
      });
            
             button_appointments_list.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene1(event, "appointments_list.fxml", "Appointments List", username, ISA);
          }
      });
             
              button_appointments_record.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene1(event, "appointments_record.fxml", "Appointments Record", username, ISA);
          }
      });
              
               button_time_manage.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene1(event, "appointment_time_management.fxml", "Manage Appointment Available Time", username, ISA);
          }
      });
         
    }
 
}
