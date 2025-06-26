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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Thanasis
 */
public class ApplicantsController implements Initializable {



    @FXML
    private TableView<Applicant> applicantsTable;
    
     

    private ObservableList<Applicant> applicantsData = FXCollections.observableArrayList();

    @FXML
   @Override
    public void initialize(URL location, ResourceBundle resources) {
                loadData();
     
    
        // Connect to the database and fetch data
    }
     private void loadData() {
        applicantsData.clear();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM applicants");

            while (resultSet.next()) {
                applicantsData.add(new Applicant(
                        resultSet.getInt("apl_id"),
                        resultSet.getString("apl_username"),
                        resultSet.getString("apl_password"),
                        resultSet.getString("apl_ISA")
                ));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set up the table columns
        TableColumn<Applicant, Integer> idColumn = (TableColumn<Applicant, Integer>) applicantsTable.getColumns().get(0);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Applicant, String> usernameColumn = (TableColumn<Applicant, String>) applicantsTable.getColumns().get(1);
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        TableColumn<Applicant, String> passwordColumn = (TableColumn<Applicant, String>) applicantsTable.getColumns().get(2);
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        TableColumn<Applicant, String> ISAColumn = (TableColumn<Applicant, String>) applicantsTable.getColumns().get(3);
        ISAColumn.setCellValueFactory(cellData -> cellData.getValue().ISAProperty());

        // Add data to the table
        applicantsTable.setItems(applicantsData);
    }

    
        @FXML
    private void deleteApplicant() {
        Applicant selectedApplicant = applicantsTable.getSelectionModel().getSelectedItem();
        if (selectedApplicant != null) {
            // Delete the selected doctor from the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("DELETE FROM applicants WHERE apl_id =?");
                statement.setInt(1, selectedApplicant.getId());
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
            alert.setContentText("Please select a applicant to delete.");
            alert.showAndWait();
        }
    }
    
    @FXML
private void addUser() {
    Applicant selectedApplicant = applicantsTable.getSelectionModel().getSelectedItem();
    
    if (selectedApplicant != null) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

            // Insert the selected applicant into the users table
            PreparedStatement insertStatement = conn.prepareStatement(
                "INSERT INTO users (username, password, ISA) VALUES (?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS // Get the new user's ID
            );
            insertStatement.setString(1, selectedApplicant.getUsername());
            insertStatement.setString(2, selectedApplicant.getPassword());
            insertStatement.setString(3, selectedApplicant.getISA());
            insertStatement.executeUpdate();

            // Retrieve the generated user ID
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1); // Get the auto-generated user ID
            }
            insertStatement.close();

            // If the applicant is a doctor, insert them into the doctors table
            if ("doctor".equalsIgnoreCase(selectedApplicant.getISA()) && userId != -1) {
                PreparedStatement insertDoctorStatement = conn.prepareStatement(
                    "INSERT INTO doctors (u_id, d_name, d_specialization) VALUES (?, ?, ?)"
                );
                insertDoctorStatement.setInt(1, userId);
                insertDoctorStatement.setString(2, selectedApplicant.getUsername());
                insertDoctorStatement.setString(3, "General Practitioner"); // Default specialty
                insertDoctorStatement.executeUpdate();
                insertDoctorStatement.close();
            }

            // Remove the applicant from the applicants table
            PreparedStatement deleteStatement = conn.prepareStatement(
                "DELETE FROM applicants WHERE apl_id = ?"
            );
            deleteStatement.setInt(1, selectedApplicant.getId());
            deleteStatement.executeUpdate();
            deleteStatement.close();

            conn.close();

            // Reload the table data to reflect the changes
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        // Show an alert if no applicant is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please select an applicant to add.");
        alert.showAndWait();
    }
}


}

    