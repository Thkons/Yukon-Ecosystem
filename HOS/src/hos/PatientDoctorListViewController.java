/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 *
 * @author Thanasis
 */
public class PatientDoctorListViewController implements Initializable {


    @FXML
    private TableView<Doctor> doctorsTable;

    

    private ObservableList<Doctor> doctorsData = FXCollections.observableArrayList();


    @FXML
   @Override
    public void initialize(URL location, ResourceBundle resources) {
                loadData();
  
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
       
        
        TableColumn<Doctor, Integer> u_idColumn = (TableColumn<Doctor, Integer>) doctorsTable.getColumns().get(0);
        u_idColumn.setCellValueFactory(cellData -> cellData.getValue().u_idProperty().asObject());

        TableColumn<Doctor, String> nameColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(1);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Doctor, String> lastNameColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(2);
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        TableColumn<Doctor, String> phoneColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(3);
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        
        TableColumn<Doctor, String> emailColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(4);
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        TableColumn<Doctor, String> specializationColumn = (TableColumn<Doctor, String>) doctorsTable.getColumns().get(5);
        specializationColumn.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());

        // Add data to the table
        doctorsTable.setItems(doctorsData);
    }

    
}

    