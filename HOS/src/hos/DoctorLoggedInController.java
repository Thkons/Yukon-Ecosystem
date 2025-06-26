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
public class DoctorLoggedInController implements Initializable {
    
    private String username;
    private String ISA;
    private int loggedInDoctorId;
     @FXML
    private Button button_AppointmentsList;
@FXML 
private Button button_logout;
@FXML 
private Label label_welcome;
@FXML 
private Button button_doctors;

@FXML 
private Button button_profile;

@FXML 
private Button button_files;

@FXML 
private Button button_past_appointments;

@FXML 
private Button button_patients;
@FXML 
private Button button_appointments;


     public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
        label_welcome.setText("WELCOME TO HOS "+  username);
    }

    // Method to set logged-in patient ID
    public void setLoggedInDoctorId(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;
        System.out.println("Logged-in Doctor ID set to: " + loggedInDoctorId); // Debugging
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         
       

        
                  button_doctors.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "d_doctors_list.fxml", "Doctors List",  username, ISA, loggedInDoctorId);
          }
      });
                  
             button_AppointmentsList.setOnAction(event -> {
            System.out.println("View Appointments clicked"); // Debugging
            DBUtils.changeScene3(event, "doctor_appointments.fxml", "View Appointments", username, ISA, loggedInDoctorId);
        });    
                 
                  button_patients.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "d_patients_list.fxml", "Patients List", username, ISA, loggedInDoctorId);
          }
      });
                  
    
             

           
             
          
      button_logout.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
          }
      });
         
          
            button_appointments.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "doctor_view_appointment.fxml", "Appointments List", username, ISA, loggedInDoctorId);
          
          }
      });
            
            button_past_appointments.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "doctor_past_appointments.fxml", "Past Appointments List", username, ISA, loggedInDoctorId);
          
          }
      });
            
            button_profile.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "doctor_profile.fxml", "Doctor Profile", username, ISA, loggedInDoctorId);
          
          }
      });
            
            button_files.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "patient_files.fxml", "Patient Files", username, ISA, loggedInDoctorId);
          
          }
      });
            
             
            
           
         
    }

}
