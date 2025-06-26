package hos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class DoctorAppointmentsController {
     private String username;
    private String ISA;
    private int loggedInDoctorId;
    
    @FXML 
    private Button button_logout; 
    
    @FXML 
    private Button button_goback;
    
    @FXML 
    private Button button_patients;
    
    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> colPatientId;

    @FXML
    private TableColumn<Appointment, Integer> colDoctorId;

    @FXML
    private TableColumn<Appointment, String> colAppointmentDate;

    @FXML
    private TableColumn<Appointment, String> colAppointmentTime;

    @FXML
    private TableColumn<Appointment, String> colStatus;

    @FXML
private TableColumn<Appointment, Void> columnConfirm; 

    

    // Set the logged-in doctor ID
    public void setLoggedInDoctorId(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;
        System.out.println("Logged-in doctor ID set to: " + loggedInDoctorId);
    }

     public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }
    // Initialize method
    @FXML
    public void initialize() {
        // Initialize table columns
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        colAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAppointmentTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Button event handlers
        button_logout.setOnAction(event -> DBUtils.changeScene0(event, "start.fxml", "HOS", null, null));
        button_goback.setOnAction(event -> DBUtils.changeScene3(event, "doctor_logged-in.fxml", "Welcome", username, ISA, loggedInDoctorId));
        button_patients.setOnAction(event -> DBUtils.changeScene2(event, "patients-list-view.fxml", "Welcome", null, null));

        
        columnConfirm.setCellFactory(param -> new TableCell<Appointment, Void>() {
        private final Button confirmButton = new Button("Confirm");

        {
            confirmButton.setOnAction(event -> {
                Appointment selectedAppointment = getTableView().getItems().get(getIndex());
                confirmAppointment(selectedAppointment);
            });
        }

        @Override
        public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(confirmButton);
            }
        }
    });
        // Load appointments
        loadAppointments();

        // Populate ComboBox
       
   
    }

    // Load unconfirmed appointments where doctor_id is NULL
    private void loadAppointments() {
        String query = "SELECT * FROM appointments WHERE status = 'Pending' AND doctor_id IS NULL";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ObservableList<Appointment> appointments = FXCollections.observableArrayList();

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("status")
                ));
            }

            appointmentsTable.setItems(appointments);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error loading appointments: " + e.getMessage(), AlertType.ERROR);
        }
    }

    // Update the status and assign the doctor to the appointment
   

    // Show alert messages
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    
    private void confirmAppointment(Appointment appointment) {
    String query = "UPDATE appointments SET status = 'Confirmed', doctor_id = ? WHERE id = ?";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, loggedInDoctorId);  // Set the doctor ID
        statement.setInt(2, appointment.getId());  // Use the appointment ID
        
        int rowsUpdated = statement.executeUpdate();
        
        if (rowsUpdated > 0) {
            System.out.println("Appointment confirmed successfully and assigned to doctor ID: " + loggedInDoctorId);
            // Refresh the appointments table after updating the status
            loadAppointments();  // Reload the appointments to reflect the status change
        } else {
            System.out.println("Failed to confirm the appointment");
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error updating appointment status: " + e.getMessage());
        alert.show();
    }
}

}
