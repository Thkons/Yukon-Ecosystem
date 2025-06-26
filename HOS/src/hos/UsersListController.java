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
public class UsersListController implements Initializable {
private String username;
    private String ISA;
   @FXML 
private Button button_logout; 
    @FXML 
private Button button_goback;
    @FXML 
private Button button_reload;
    @FXML 
private Button button_applicants; 
    @FXML
    private TableView<User> usersTable;
    
     
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField ISAField;

    private ObservableList<User> usersData = FXCollections.observableArrayList();

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
       button_applicants.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          DBUtils.changeScene2(event, "applicants.fxml", "Applicants List", null, null);
          }
      });    
       button_reload.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event)
          {
          loadData();
          }
      });    
        // Connect to the database and fetch data
    }
     private void loadData() {
        usersData.clear();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                usersData.add(new User(
                        resultSet.getInt("u_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("ISA")
                ));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set up the table columns
        TableColumn<User, Integer> idColumn = (TableColumn<User, Integer>) usersTable.getColumns().get(0);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<User, String> usernameColumn = (TableColumn<User, String>) usersTable.getColumns().get(1);
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        TableColumn<User, String> passwordColumn = (TableColumn<User, String>) usersTable.getColumns().get(2);
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        TableColumn<User, String> ISAColumn = (TableColumn<User, String>) usersTable.getColumns().get(3);
        ISAColumn.setCellValueFactory(cellData -> cellData.getValue().ISAProperty());

        // Add data to the table
        usersTable.setItems(usersData);
    }

    @FXML
    private void addUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String ISA = ISAField.getText();

        // Insert the new doctor into the database
       
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            PreparedStatement statement = conn.prepareStatement("INSERT INTO users (username, password, ISA) VALUES (?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, ISA);
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reload the table data
        loadData();
       
        // Clear input fields
       usernameField.clear();
        passwordField.clear();
        ISAField.clear();
    }

    @FXML
    private void editUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE users SET username=?, ISA=? WHERE u_id=?");
                statement.setString(1, usernameField.getText());
                statement.setString(2, ISAField.getText());
                statement.setInt(3, selectedUser.getId());
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
            alert.setContentText("Please select a user to edit.");
            alert.showAndWait();
        }
    }
     @FXML
    private void editUsername() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE users SET username=? WHERE u_id=?");
                statement.setString(1, usernameField.getText());        
                statement.setInt(2, selectedUser.getId());
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
            alert.setContentText("Please select a user to edit.");
            alert.showAndWait();
        }
    }
     @FXML
    private void editISA() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE users SET ISA=? WHERE u_id=?");
                statement.setString(1, ISAField.getText());        
                statement.setInt(2, selectedUser.getId());
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
            alert.setContentText("Please select a user to edit.");
            alert.showAndWait();
        }
    }
        @FXML
    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Delete the selected doctor from the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("DELETE FROM users WHERE u_id=?");
                statement.setInt(1, selectedUser.getId());
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
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }
}

    