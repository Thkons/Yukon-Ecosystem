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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Thanasis
 */
public class DoctorsListController implements Initializable {
    
private String username;
    private String ISA;
    @FXML 
    private Button button_logout; 
    @FXML 
    private Button button_goback;
    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField specializationField;
    
    @FXML
    private DatePicker datePicker; // Date Picker for selecting the date
    @FXML
    private ComboBox<String> timeComboBox; // ComboBox for selecting the time
    

    private ObservableList<Doctor> doctorsData = FXCollections.observableArrayList();

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
        doctorsData.clear();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM doctors");

            while (resultSet.next()) {
                doctorsData.add(new Doctor(
                        resultSet.getInt("d_id"),
                        resultSet.getInt("u_id"),
                        resultSet.getString("d_name"),
                        resultSet.getString("d_lastname"),
                        resultSet.getString("d_phone"),
                        resultSet.getString("d_email"),
                        resultSet.getString("d_specialization")
                ));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set up the table columns
         TableColumn<Doctor, Integer> idColumn = (TableColumn<Doctor, Integer>) doctorsTable.getColumns().get(0);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        
        TableColumn<Doctor, Integer> u_idColumn = (TableColumn<Doctor, Integer>) doctorsTable.getColumns().get(1);
        u_idColumn.setCellValueFactory(cellData -> cellData.getValue().u_idProperty().asObject());

        TableColumn<Doctor, String> nameColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(2);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Doctor, String> lastNameColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(3);
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        TableColumn<Doctor, String> phoneColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(4);
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        
        TableColumn<Doctor, String> emailColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(5);
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        TableColumn<Doctor, String> specializationColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(6);
        specializationColumn.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());

        // Add data to the table
        doctorsTable.setItems(doctorsData);
    }

    @FXML
    private void editDoctor() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE doctors SET d_name=?, d_lastname=?, d_specialisation=? WHERE d_id=?");
                statement.setString(1, nameField.getText());
                statement.setString(2, lastNameField.getText());
                statement.setString(3, specializationField.getText());
                statement.setInt(4, selectedDoctor.getId());
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
            alert.setContentText("Please select a doctor to edit.");
            alert.showAndWait();
        }
    }
    @FXML
    private void editName() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE doctors SET d_name=? WHERE d_id=?");
                statement.setString(1, nameField.getText());
                statement.setInt(2, selectedDoctor.getId());
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
            alert.setContentText("Please select a doctor to edit.");
            alert.showAndWait();
        }
    }
    @FXML
    private void editLastName() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE doctors SET d_lastname=? WHERE d_id=?");
                statement.setString(1, lastNameField.getText());

                statement.setInt(2, selectedDoctor.getId());
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
            alert.setContentText("Please select a doctor to edit.");
            alert.showAndWait();
        }
    }
    @FXML
    private void editSpecialization() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE doctors SET d_specialisation=? WHERE d_id=?");
                statement.setString(1, specializationField.getText());
                statement.setInt(2, selectedDoctor.getId());
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
            alert.setContentText("Please select a doctor to edit.");
            alert.showAndWait();
        }
    }
    
        @FXML
    private void deleteDoctor() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            // Delete the selected doctor from the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("DELETE FROM doctors WHERE d_id=?");
                statement.setInt(1, selectedDoctor.getId());
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
            alert.setContentText("Please select a doctor to delete.");
            alert.showAndWait();
        }
    }
    
}

    