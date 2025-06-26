package hos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

public class AppointmentController {
     private String username;
    private String ISA;

    private int loggedInPatientId;  // Variable to hold the logged-in patient ID

    @FXML
    private Button button_goBack;
    
     @FXML
    private Button button_logout;

    @FXML
    private Button button_bookAppointment;  // Button for booking the appointment

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    // This method is called to set the logged-in patient ID from the previous scene
    public void setLoggedInPatientId(int loggedInPatientId) {
        this.loggedInPatientId = loggedInPatientId;
        System.out.println("Logged-in patient ID set to: " + loggedInPatientId);  // Debugging
    }
    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }

    // Called when the screen is initialized
    @FXML
    public void initialize() {
           loadAvailableDatesAndTimes();
        // Populating timeComboBox with some example times

        // Handling the "Go Back" button click
        button_goBack.setOnAction(event -> goBack(event));
        
         button_logout.setOnAction(event -> {
            System.out.println("Logout clicked"); // Debugging
            // Implement logic for logging out the user and returning to the login screen
            DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
        });

        // Handling the "Book Appointment" button click
        button_bookAppointment.setOnAction(event -> bookAppointment(event));  // Book appointment method is bound to the button
    }
    
   private void loadAvailableDatesAndTimes() {
    // Load available dates
    String dateQuery = "SELECT DISTINCT date FROM available_dates ORDER BY date ASC";

    try (Connection conn = DBUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(dateQuery);
         ResultSet rs = stmt.executeQuery()) {

        ObservableList<java.time.LocalDate> availableDates = FXCollections.observableArrayList();

        while (rs.next()) {
            availableDates.add(rs.getDate("date").toLocalDate());
        }

        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && !availableDates.contains(item)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Highlight unavailable dates
                }
            }
        });

    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Load available time slots
    String timeQuery = "SELECT time_slot FROM available_times";

    try (Connection conn = DBUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(timeQuery);
         ResultSet rs = stmt.executeQuery()) {

        ObservableList<String> timeSlots = FXCollections.observableArrayList();

        while (rs.next()) {
            timeSlots.add(rs.getString("time_slot"));
        }

        timeComboBox.setItems(timeSlots);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Called when the "Book Appointment" button is clicked
    @FXML
    private void bookAppointment(ActionEvent event) {
        // Ensure that both a date and time are selected
        if (datePicker.getValue() == null || timeComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select both a date and a time.");
            alert.show();
            return;
        }

        // Make sure the patient ID is available
        if (loggedInPatientId == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Patient ID is not set. Please log in first.");
            alert.show();
            return;
        }

        // Extract the date and time
        String date = datePicker.getValue().toString();
        String time = timeComboBox.getSelectionModel().getSelectedItem();
        
        
        String query = "INSERT INTO appointments (patient_id, date, time, status) VALUES (?, ?, ?, ?)";
        
         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedInPatientId);
            statement.setDate(2, Date.valueOf(date));
            statement.setString(3, time);
            statement.setString(4, "Pending");

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
               Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Appointment booked successfully!");
            alert.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Failed to book the appointment. Please try again.");
            alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("An error occurred while booking the appointment: " + e.getMessage());
        alert.show();
        }

        // Simulate appointment booking by printing the details (replace with actual database call)
        
    }

    // Go back to the previous scene (patient_logged-in.fxml)
    private void goBack(ActionEvent event) {
        // Navigate back to the logged-in patient's screen (this depends on how you implement scene transitions)
        DBUtils.changeScene5(event, "patient_logged-in.fxml", "Patient Logged In", username, ISA, loggedInPatientId);
    }
}
