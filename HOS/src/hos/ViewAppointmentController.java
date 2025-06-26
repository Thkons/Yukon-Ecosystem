/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

/**
 *
 * @author Thanasis
 */
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

public class ViewAppointmentController {
     private String username;
    private String ISA;

    private int loggedInPatientId;

    @FXML
    private Button button_goBack;
    
    @FXML
    private Button button_logout;
    
     @FXML
    private Button button_doctors;
    
    @FXML
private TableColumn<Appointment, Void> columnCancel; // Add this in the FXML file for the cancel button column
    
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

    public void setLoggedInPatientId(int loggedInPatientId) {
        this.loggedInPatientId = loggedInPatientId;
        System.out.println("Viewing appointments for patient ID: " + loggedInPatientId);
        loadAppointments();
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
    
    columnCancel.setCellFactory(param -> new TableCell<Appointment, Void>() {
        private final Button cancelButton = new Button("Cancel");

        {
            cancelButton.setOnAction(event -> {
                Appointment selectedAppointment = getTableView().getItems().get(getIndex());
                cancelAppointment(selectedAppointment);
            });
        }

        @Override
        public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(cancelButton);
            }
        }
    });

    button_logout.setOnAction(event -> {
        System.out.println("Logout clicked");
        DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
    });

    button_goBack.setOnAction(event -> {
        System.out.println("Go Back clicked");
        DBUtils.changeScene5(event, "patient_logged-in.fxml", "Welcome", username, ISA, loggedInPatientId);
    });
    
    button_doctors.setOnAction(event -> {
        System.out.println("Logout clicked");
        DBUtils.changeScene2(event, "patient_doctor_list_view.fxml", "HOS", null, null);
    });
}


    private void loadAppointments() {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
   String query = "SELECT * FROM appointments WHERE patient_id = ? AND (status = 'Confirmed' OR status = 'Pending')";
    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, loggedInPatientId);  // Use the logged-in doctor's ID
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

private void cancelAppointment(Appointment appointment) {
    String query = "UPDATE appointments SET status = 'Cancelled' WHERE id = ?";
    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, appointment.getId());  // Use the appointment ID
        int rowsUpdated = statement.executeUpdate();
        
        if (rowsUpdated > 0) {
            System.out.println("Appointment cancelled successfully");
            // Refresh the appointments table after updating the status
            loadAppointments();  // Reload the appointments to reflect the status change
        } else {
            System.out.println("Failed to cancel the appointment");
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error updating appointment status: " + e.getMessage());
        alert.show();
    }
}
   
}
