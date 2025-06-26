/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Thanasis
 */
public class DPatientsListController implements Initializable {
    private String username;
    private String ISA;
private int loggedInDoctorId;
   @FXML 
private Button button_logout; 
    @FXML 
private Button button_goback;

    @FXML
    private TableView<Patient> patientsTable;
   

    @FXML
    private TextField symptomsField;
    

    @FXML
    private TextField treatmentField;
    

    private ObservableList<Patient> patientsData = FXCollections.observableArrayList();
    
    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }

    public void setLoggedInDoctorId(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;
        System.out.println("Logged-in doctor ID set to: " + loggedInDoctorId);  // Debugging
    }
    @FXML
   @Override
    public void initialize(URL location, ResourceBundle resources) {
                loadData();
      button_logout.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
          }
      });
       button_goback.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene3(event, "doctor_logged-in.fxml", "Welcome", username, ISA, loggedInDoctorId);
          }
      });    
        // Connect to the database and fetch data
    }
    private void loadData() {
    patientsData.clear();

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         Statement statement = conn.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT * FROM patients")) {

        while (resultSet.next()) {
            patientsData.add(new Patient(
                    resultSet.getInt("p_id"),
                    resultSet.getInt("u_id"),
                    resultSet.getString("p_name"),
                    resultSet.getString("p_lastname"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("p_symptoms"),
                    resultSet.getString("p_treatment"),
                    resultSet.getString("dob")
            ));
        }
    } catch (SQLException e) {
        showErrorDialog("Database Error", "Unable to load patient data.", e);
    }

    // Set up the table columns
    

    TableColumn<Patient, Integer> u_idColumn = (TableColumn<Patient, Integer>) patientsTable.getColumns().get(0);
    u_idColumn.setCellValueFactory(cellData -> cellData.getValue().u_idProperty().asObject());

    TableColumn<Patient, String> nameColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(1);
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    TableColumn<Patient, String> lastNameColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(2);
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

    TableColumn<Patient, String> emailColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(3);
    emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

    TableColumn<Patient, String> phoneColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(4);
    phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

    TableColumn<Patient, String> symptomsColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(5);
    symptomsColumn.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());

    TableColumn<Patient, String> treatmentColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(6);
    treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
    
    TableColumn<Patient, String> dobColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(7);
    dobColumn.setCellValueFactory(cellData -> cellData.getValue().dobProperty());



    // Add data to the table
    patientsTable.setItems(patientsData);
}

   
    
    
    

private void showErrorDialog(String title, String message, Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    e.printStackTrace();  // Print stack trace for debugging purposes
}

    

    
    
     @FXML
    private void editTreatment() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE patients SET p_treatment=? WHERE p_id=?");
                statement.setString(1, treatmentField.getText());
                statement.setInt(2, selectedPatient.getId());
                statement.executeUpdate();

                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Reload the table data
            loadData();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a patient to edit.");
            alert.showAndWait();
        }
    }
     @FXML
    private void editSymptoms() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE patients SET p_symptoms=? WHERE p_id=?");
                statement.setString(1, symptomsField.getText());
                statement.setInt(2, selectedPatient.getId());
                statement.executeUpdate();

                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Reload the table data
            loadData();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a patient to edit.");
            alert.showAndWait();
        }
    }
    
@FXML
private void deletePatient() {
    Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
    
    if (selectedPatient != null) {
        // Confirm the deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Are you sure you want to delete this patient?");
        confirmAlert.setContentText("This action cannot be undone.");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                 PreparedStatement deletePatientStatement = conn.prepareStatement("DELETE FROM patients WHERE p_id=?");
                 PreparedStatement deleteFilesStatement = conn.prepareStatement("DELETE FROM patient_files WHERE patient_id=?")) {
                
                // Delete the patient
                deletePatientStatement.setInt(1, selectedPatient.getId());
                deletePatientStatement.executeUpdate();
                
                // Delete the patient's related files
                deleteFilesStatement.setInt(1, selectedPatient.getId());
                deleteFilesStatement.executeUpdate();
                
                // Inform the user about success
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Patient and associated files have been successfully deleted.");
                successAlert.showAndWait();
                
                // Reload the table data
                loadData();
            } catch (SQLException e) {
                showErrorDialog("Error Deleting Patient", "An error occurred while deleting the patient and their files.", e);
            }
        }
    } else {
        // Show a warning if no patient is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please select a patient to delete.");
        alert.showAndWait();
    }
}

}


    