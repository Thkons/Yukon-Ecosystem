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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Thanasis
 */
public class PatientsListController implements Initializable {
private String username;
    private String ISA;
   @FXML 
private Button button_logout; 
    @FXML 
private Button button_goback;

    @FXML
    private TableView<Patient> patientsTable;
   

    @FXML
    private TextField symptomsField;
    
      @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;
    
     @FXML
    private TextField treatmentField;
    

    private ObservableList<Patient> patientsData = FXCollections.observableArrayList();

    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
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
          DBUtils.changeScene1(event, "logged-in_medical-secretary.fxml", "Welcome", username, ISA);
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
    TableColumn<Patient, Integer> idColumn = (TableColumn<Patient, Integer>) patientsTable.getColumns().get(0);
    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

    TableColumn<Patient, Integer> u_idColumn = (TableColumn<Patient, Integer>) patientsTable.getColumns().get(1);
    u_idColumn.setCellValueFactory(cellData -> cellData.getValue().u_idProperty().asObject());

    TableColumn<Patient, String> nameColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(2);
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    TableColumn<Patient, String> lastNameColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(3);
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

    TableColumn<Patient, String> emailColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(4);
    emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

    TableColumn<Patient, String> phoneColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(5);
    phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

    TableColumn<Patient, String> symptomsColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(6);
    symptomsColumn.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());

    TableColumn<Patient, String> treatmentColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(7);
    treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
    
    TableColumn<Patient, String> dobColumn = (TableColumn<Patient, String>) patientsTable.getColumns().get(8);
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
    e.printStackTrace();
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
    private void addPatient() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String symptoms = symptomsField.getText();
        String treatment = treatmentField.getText();

        // Insert the new doctor into the database
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            PreparedStatement statement = conn.prepareStatement("INSERT INTO patients (p_name, p_lastname, p_symptoms, p_treatment) VALUES (?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, symptoms);
            statement.setString(4, treatment);
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reload the table data
        loadData();

        // Clear input fields
        nameField.clear();
        lastNameField.clear();
        symptomsField.clear();
        treatmentField.clear();
    }

    @FXML
    private void editPatient() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE patients SET p_name=?, p_lastname=?, p_symptoms=?, p_treatment WHERE p_id=?");
                statement.setString(1, nameField.getText());
                statement.setString(2, lastNameField.getText());
                statement.setString(3, symptomsField.getText());
                statement.setString(4, treatmentField.getText());
                statement.setInt(5, selectedPatient.getId());
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
    private void editName() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE patients SET p_name=? WHERE p_id=?");
                statement.setString(1, nameField.getText());
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
    private void editLastName() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE patients SET p_lastname=? WHERE p_id=?");
                statement.setString(1, lastNameField.getText());
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
        Patient selectedPatient =patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Delete the selected doctor from the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("DELETE FROM patients WHERE p_id=?");
                statement.setInt(1, selectedPatient.getId());
                statement.executeUpdate();

                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Reload the table data
            loadData();
        } else {
            // If no doctor is selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a patient to delete.");
            alert.showAndWait();
        }
    }
}


    