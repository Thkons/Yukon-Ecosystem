/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Thanasis
 */
public class DoctorPastAppointmentsController {

private int loggedInDoctorId;



 private String username;
    private String ISA;
    @FXML
    private Button button_goBack;
    
    @FXML
    private Button button_logout;
    
    @FXML 
    private Button button_patients;
    
    @FXML
    private TableView<Appointment> tableAppointments;
    
    
    @FXML
    private TableColumn<Appointment, Integer> columnPatientId;
    
    @FXML
    private TableColumn<Appointment, Integer> columnDoctorId;
    
    
    @FXML
    private TableColumn<Appointment, String> columnDate;
    
    @FXML
    private TableColumn<Appointment, String> columnTime;
    
    @FXML
    private TableColumn<Appointment, String> columnStatus;

    public void setLoggedInDoctorId(int loggedInDoctorId) {
    this.loggedInDoctorId = loggedInDoctorId;
    System.out.println("Viewing appointments for doctor ID: " + loggedInDoctorId);
    loadAppointments(); // Load appointments for this doctor
}
    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }


    @FXML
public void initialize() {
    columnPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
    columnDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
    columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    
    // Create the Cancel button for each row
   

    button_logout.setOnAction(event -> {
        System.out.println("Logout clicked");
        DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
    });

    button_goBack.setOnAction(event -> {
        System.out.println("Go Back clicked");
        DBUtils.changeScene3(event, "doctor_logged-in.fxml", "Welcome", username, ISA, loggedInDoctorId);
    });
     button_patients.setOnAction(event -> DBUtils.changeScene2(event, "patients-list-view.fxml", "Welcome", null, null));
}


    private void loadAppointments() {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    String query = "SELECT * FROM appointments WHERE doctor_id = ? AND status = 'Completed'";  // Filter by doctor_id
    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, loggedInDoctorId);  // Use the logged-in doctor's ID
        try (ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                appointments.add(new Appointment(
                        resultSet.getInt("id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("date"),
                        resultSet.getString("time"),
                        resultSet.getString("status")));
            }
        }
        tableAppointments.setItems(appointments);
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error loading appointments: " + e.getMessage());
        alert.show();
    }
}

   




   
}
