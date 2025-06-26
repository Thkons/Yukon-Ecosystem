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
public class FeedBackListController implements Initializable {
    private String username;
    private String ISA;
   @FXML 
private Button button_logout; 
    @FXML 
private Button button_goback;
    @FXML
    private TableView<FeedBack> feedBackTable;

    private int loggedInSupportTeamId;

    private ObservableList<FeedBack> feedBackData = FXCollections.observableArrayList();

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
        feedBackData.clear();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db1", "root", "");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM messages");

            while (resultSet.next()) {
                feedBackData.add(new FeedBack(
                        resultSet.getInt("m_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("subject"),
                        resultSet.getString("feed_back"),
                        resultSet.getString("message")
                ));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set up the table columns
        TableColumn<FeedBack, Integer> idColumn = (TableColumn<FeedBack, Integer>) feedBackTable.getColumns().get(0);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<FeedBack, String> nameColumn = (TableColumn<FeedBack, String>) feedBackTable.getColumns().get(1);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<FeedBack, String> emailColumn = (TableColumn<FeedBack, String>) feedBackTable.getColumns().get(2);
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        TableColumn<FeedBack, String> subjectColumn = (TableColumn<FeedBack, String>) feedBackTable.getColumns().get(3);
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        
        TableColumn<FeedBack, String> feedBackColumn = (TableColumn<FeedBack, String>) feedBackTable.getColumns().get(4);
        feedBackColumn.setCellValueFactory(cellData -> cellData.getValue().feedBackProperty());
        
        TableColumn<FeedBack, String> messageColumn = (TableColumn<FeedBack, String>) feedBackTable.getColumns().get(5);
        messageColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());

        // Add data to the table
        feedBackTable.setItems(feedBackData);
    }


    
    
        @FXML
    private void deleteFeedBack() {
        FeedBack selectedFeedBack = feedBackTable.getSelectionModel().getSelectedItem();
        if (selectedFeedBack != null) {
            // Delete the selected doctor from the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db1", "root", "");
                PreparedStatement statement = conn.prepareStatement("DELETE FROM messages WHERE m_id=?");
                statement.setInt(1, selectedFeedBack.getId());
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
            alert.setContentText("Please select a feedback to delete.");
            alert.showAndWait();
        }
    }
    
}

    