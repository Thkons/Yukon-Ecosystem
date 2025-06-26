package hos;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentsListController{

    private String username;
    private String ISA;
    
    @FXML private Button button_logout;
    @FXML private Button button_goback;
    @FXML private Button button_doctors;
    @FXML private Button button_patients;
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
@FXML private TableColumn<Appointment, Void> columnCancel; 
@FXML private TableColumn<Appointment, Void> columnComplete;
@FXML private TableColumn<Appointment, Void> columnPending;

@FXML
private DatePicker datePicker;
@FXML
private ComboBox<String> timeComboBox; 


   public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }


    @FXML
    private void initialize() {
    loadAppointments();
 loadAppointments();
    columnPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
    columnDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
    columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

// Cancel Button Cell Factory
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
            setGraphic(empty ? null : cancelButton);
        }
    });

    // Complete Button Cell Factory
    columnComplete.setCellFactory(param -> new TableCell<Appointment, Void>() {
        private final Button completeButton = new Button("Complete");

        {
            completeButton.setOnAction(event -> {
                Appointment selectedAppointment = getTableView().getItems().get(getIndex());
                completeAppointment(selectedAppointment);
            });
        }

        @Override
        public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : completeButton);
        }
    });

    // Pending Button Cell Factory
    columnPending.setCellFactory(param -> new TableCell<Appointment, Void>() {
        private final Button pendingButton = new Button("Pending");

        {
            pendingButton.setOnAction(event -> {
                Appointment selectedAppointment = getTableView().getItems().get(getIndex());
                pendingAppointment(selectedAppointment);
            });
        }

        @Override
        public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : pendingButton);
        }
    });

    // Go back button
    button_goback.setOnAction(event -> DBUtils.changeScene1(event, "logged-in_medical-secretary.fxml", "Patient Dashboard", username, ISA));
    
    // Logout button
    button_logout.setOnAction(event -> DBUtils.changeScene0(event, "start.fxml", "HOS", null, null));
    
    button_doctors.setOnAction(event -> DBUtils.changeScene2(event, "patient_doctor_list_view.fxml", "Doctors List", null, null));
    
    button_patients.setOnAction(event -> DBUtils.changeScene2(event, "patients-list-view.fxml", "Patients List", null, null));

     timeComboBox.getItems().addAll(
        "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM"
    );
    }


    // Method to load the appointments from the database
     private void loadAppointments() {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    String query = "SELECT * FROM appointments";  
    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
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
    
      private void completeAppointment(Appointment appointment) {
    String query = "UPDATE appointments SET status = 'Completed' WHERE id = ?";

    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, appointment.getId());  // Use the appointment ID
        int rowsUpdated = statement.executeUpdate();
        
        if (rowsUpdated > 0) {
            System.out.println("Appointment completed successfully");
            // Refresh the appointments table after updating the status
            loadAppointments();  // Reload the appointments to reflect the status change
        } else {
            System.out.println("Failed to complete the appointment");
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error updating appointment status: " + e.getMessage());
        alert.show();
    }
}
      
         private void pendingAppointment(Appointment appointment) {
    String query = "UPDATE appointments SET status = 'Pending', doctor_id = NULL WHERE id = ?";

    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, appointment.getId());  // Use the appointment ID
        int rowsUpdated = statement.executeUpdate();
        
        if (rowsUpdated > 0) {
            System.out.println("Appointment changed to pending successfully");
            // Refresh the appointments table after updating the status
            loadAppointments();  // Reload the appointments to reflect the status change
        } else {
            System.out.println("Failed to change the appointment");
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error updating appointment status: " + e.getMessage());
        alert.show();
    }
}

    @FXML
private void editDate() {
    Appointment selectedAppointment = tableAppointments.getSelectionModel().getSelectedItem();

    if (selectedAppointment != null) {
        if (datePicker.getValue() != null) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE appointments SET date=? WHERE id=?");

                statement.setString(1, datePicker.getValue().toString()); // Get selected date
                statement.setInt(2, selectedAppointment.getId());

                statement.executeUpdate();
                statement.close();
                conn.close();

                // Refresh the table data
                loadAppointments();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Warning", "Please select a valid date.");
        }
    } else {
        showAlert("Warning", "Please select an appointment to edit.");
    }
}

// Edit Time Method
@FXML
private void editTime() {
    Appointment selectedAppointment = tableAppointments.getSelectionModel().getSelectedItem();

    if (selectedAppointment != null) {
        if (timeComboBox.getValue() != null) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE appointments SET time=? WHERE id=?");

                statement.setString(1, timeComboBox.getValue()); // Get selected time
                statement.setInt(2, selectedAppointment.getId());

                statement.executeUpdate();
                statement.close();
                conn.close();

                // Refresh the table data
                loadAppointments();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Warning", "Please select a valid time.");
        }
    } else {
        showAlert("Warning", "Please select an appointment to edit.");
    }
}

// Helper method to show alerts
private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();

}
}