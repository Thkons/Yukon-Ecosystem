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
public class PatientsListViewController implements Initializable {


    @FXML
    private TableView<Patient> patientsTable;
   

   

    private ObservableList<Patient> patientsData = FXCollections.observableArrayList();

    
    @FXML
   @Override
    public void initialize(URL location, ResourceBundle resources) {
                loadData();
      
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
    e.printStackTrace();
}

    

    
    
     
}


    