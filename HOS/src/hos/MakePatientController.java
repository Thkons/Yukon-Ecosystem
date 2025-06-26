package hos;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

public class MakePatientController implements Initializable {

    private int u_id;  // Store the passed u_id

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_lastName;
    
    @FXML
    private TextField tf_email;
    
    @FXML
    private TextField tf_phone;

    @FXML
    private TextField tf_symptoms;
    
     @FXML
    private DatePicker dob;

    @FXML
    private Button button_createPatient;
    
    @FXML
    private Button button_goBack;

    public void setUserId(int u_id) {
        this.u_id = u_id;  // Set the passed u_id from the previous scene
    }

    @FXML
    private void createPatient(ActionEvent event) {
        
        if (dob.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a date");
            alert.show();
            return;
        }
        String name = tf_name.getText().trim();
        String lastName = tf_lastName.getText().trim();
        String email = tf_email.getText().trim();
        String phone = tf_phone.getText().trim();
        String symptoms = tf_symptoms.getText().trim();
        String date = dob.getValue().toString();

        // Check if any field is empty
        if (name.isEmpty() || lastName.isEmpty() || symptoms.isEmpty() || phone.isEmpty() || email.isEmpty() || date.isEmpty()) {
            showAlert("Error", "Please fill in all the fields.", Alert.AlertType.ERROR);
            return;
        }

        // Insert the patient into the database
        String query = "INSERT INTO patients (p_name, p_lastname, email, phone, p_symptoms, dob, p_treatment, u_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, symptoms);
            statement.setDate(6, Date.valueOf(date));
            statement.setNull(7, java.sql.Types.VARCHAR);  // No treatment yet
            statement.setInt(8, u_id);  // Link the patient to the same u_id as the user

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Patient profile created!", Alert.AlertType.INFORMATION);
                DBUtils.changeScene0(event, "patient.fxml", "Patient Dashboard", null, null);
            } else {
                showAlert("Error", "Failed to create patient profile.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_createPatient.setOnAction(this::createPatient);
        button_goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene0(event, "register_application.fxml", "HOS", null, null);
            }
        });
    }
}
