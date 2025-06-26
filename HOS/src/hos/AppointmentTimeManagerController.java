package hos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
public class AppointmentTimeManagerController  {
    private String username;
    private String ISA;
    @FXML
    private TableView<String> dateTable;
    @FXML
    private TableColumn<String, String> dateColumn;
    @FXML
    private TableView<String> timeTable;
    @FXML
    private TableColumn<String, String> timeColumn;

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> timePicker;
    
    @FXML
    private Button button_goBack;
    
     @FXML
    private Button button_logout;

    @FXML
    private Button addDateButton, removeDateButton, addTimeButton, removeTimeButton;

    private ObservableList<String> availableDates = FXCollections.observableArrayList();
    private ObservableList<String> availableTimes = FXCollections.observableArrayList();
    
     public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }


    @FXML
    public void initialize() {
        loadAvailableDates();
        loadAvailableTimes();
        
        dateTable.setItems(availableDates);
        timeTable.setItems(availableTimes);

        dateColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        timeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        
         button_goBack.setOnAction(event -> goBack(event));
        
         button_logout.setOnAction(event -> {
            System.out.println("Logout clicked"); // Debugging
            // Implement logic for logging out the user and returning to the login screen
            DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
        });
         
        timePicker.setItems(FXCollections.observableArrayList("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM"));
    }

    private void loadAvailableDates() {
        availableDates.clear();
        String query = "SELECT DISTINCT date FROM available_dates ORDER BY date ASC";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                availableDates.add(rs.getDate("date").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAvailableTimes() {
        availableTimes.clear();
        String query = "SELECT time_slot FROM available_times";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                availableTimes.add(rs.getString("time_slot"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            String query = "INSERT INTO available_dates (date) VALUES (?)";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, Date.valueOf(selectedDate));
                stmt.executeUpdate();
                availableDates.add(selectedDate.toString());
                datePicker.setValue(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void removeDate(ActionEvent event) {
        String selectedDate = dateTable.getSelectionModel().getSelectedItem();
        if (selectedDate != null) {
            String query = "DELETE FROM available_dates WHERE date = ?";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, Date.valueOf(selectedDate));
                stmt.executeUpdate();
                availableDates.remove(selectedDate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


     @FXML
    private void addTime(ActionEvent event) {
        String selectedTime = timePicker.getSelectionModel().getSelectedItem();
        if (selectedTime != null) {
            String query = "INSERT INTO available_times (time_slot) VALUES (?)";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, selectedTime);
                stmt.executeUpdate();
                availableTimes.add(selectedTime);
                timePicker.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void removeTime(ActionEvent event) {
        String selectedTime = timeTable.getSelectionModel().getSelectedItem();
        if (selectedTime != null) {
            String query = "DELETE FROM available_times WHERE time_slot = ?";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, selectedTime);
                stmt.executeUpdate();
                availableTimes.remove(selectedTime);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
     private void goBack(ActionEvent event) {
        // Navigate back to the logged-in patient's screen (this depends on how you implement scene transitions)
        DBUtils.changeScene1(event, "logged-in_medical-secretary.fxml", "Welcome", username, ISA);
    }
}
