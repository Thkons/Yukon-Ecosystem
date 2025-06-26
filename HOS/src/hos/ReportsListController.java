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
public class ReportsListController implements Initializable {
    private String username;
    private String ISA;
   @FXML 
private Button button_logout; 
    @FXML 
private Button button_goback;
    @FXML
    private TableView<Report> reportsTable;
@FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField problemField;
    
    @FXML
    private TextField reportField;
    
    private int loggedInSupportTeamId;


    private ObservableList<Report> reportsData = FXCollections.observableArrayList();

    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }
      public void setLoggedInSupportTeamId(int loggedInSupportTeamId) {
        this.loggedInSupportTeamId = loggedInSupportTeamId;
        System.out.println("Logged-in Support Team Member ID set to: " + loggedInSupportTeamId); // Debugging
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
          DBUtils.changeScene6(event, "logged-in_support-team.fxml", "Welcome", username, ISA, loggedInSupportTeamId);
          }
      });    
        // Connect to the database and fetch data
    }
private void loadData() {
    reportsData.clear();

    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        
        // Use prepared statement to set the m_id parameter
        String query = "SELECT * FROM reports WHERE support_member_id=?";
        PreparedStatement statement = conn.prepareStatement(query);

      
        statement.setInt(1, loggedInSupportTeamId);  

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
           
            reportsData.add(new Report(
                    resultSet.getInt("r_id"),
                    resultSet.getInt("support_member_id"),
                    resultSet.getString("support_member_name"),
                    resultSet.getString("support_member_lastname"),
                    resultSet.getString("problem_name"),
                    resultSet.getString("report")
            ));
        }

        resultSet.close();
        statement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Set up the table columns
    TableColumn<Report, Integer> idColumn = (TableColumn<Report, Integer>) reportsTable.getColumns().get(0);
    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

    TableColumn<Report, Integer> m_idColumn = (TableColumn<Report, Integer>) reportsTable.getColumns().get(1);
    m_idColumn.setCellValueFactory(cellData -> cellData.getValue().m_idProperty().asObject());

    TableColumn<Report, String> nameColumn = (TableColumn<Report, String>) reportsTable.getColumns().get(2);
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    TableColumn<Report, String> lastNameColumn = (TableColumn<Report, String>) reportsTable.getColumns().get(3);
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

    TableColumn<Report, String> problemColumn = (TableColumn<Report, String>) reportsTable.getColumns().get(4);
    problemColumn.setCellValueFactory(cellData -> cellData.getValue().problemProperty());

    TableColumn<Report, String> reportColumn = (TableColumn<Report, String>) reportsTable.getColumns().get(5);
    reportColumn.setCellValueFactory(cellData -> cellData.getValue().reportProperty());

    // Add data to the table
    reportsTable.setItems(reportsData);
}

@FXML
    private void addReport() {
        
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String problem = problemField.getText();
        String report = reportField.getText();

        // Insert the new doctor into the database
       
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            PreparedStatement statement = conn.prepareStatement("INSERT INTO reports (support_member_id, support_member_name, support_member_lastname, problem_name, report) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, loggedInSupportTeamId);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setString(4, problem);
            statement.setString(5, report);
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
        problemField.clear();
        reportField.clear();
    }

    @FXML
    private void editReports() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE reports SET  support_member_name=?, support_member_lastname=?, problem_name=?, report=? WHERE r_id=?");
                statement.setString(1, nameField.getText());
                statement.setString(2, lastNameField.getText());
                statement.setString(3, problemField.getText());
                statement.setString(4, reportField.getText());
                statement.setInt(5, selectedReport.getId());
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
            alert.setContentText("Please select a report to edit.");
            alert.showAndWait();
        }
    }
    @FXML
    private void editName() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE reports SET  support_member_name=? WHERE r_id=?");
                statement.setString(1, nameField.getText());
                statement.setInt(2, selectedReport.getId());
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
            alert.setContentText("Please select a report to edit.");
            alert.showAndWait();
        }
    }
    @FXML
    private void editLastName() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE reports SET support_member_lastname=? WHERE r_id=?");
                statement.setString(1, lastNameField.getText());

                statement.setInt(2, selectedReport.getId());
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
    private void editProblem() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE reports SET problem_name=? WHERE r_id=?");
                statement.setString(1, problemField.getText());
                statement.setInt(2, selectedReport.getId());
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
            alert.setContentText("Please select a report to edit.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void editReport() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Update the selected doctor's information in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("UPDATE reports SET report=? WHERE r_id=?");
                statement.setString(1, reportField.getText());
                statement.setInt(2, selectedReport.getId());
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
            alert.setContentText("Please select a report to edit.");
            alert.showAndWait();
        }
    }
    
        @FXML
    private void deleteReport() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Delete the selected doctor from the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                PreparedStatement statement = conn.prepareStatement("DELETE FROM reports WHERE r_id=?");
                statement.setInt(1, selectedReport.getId());
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

    