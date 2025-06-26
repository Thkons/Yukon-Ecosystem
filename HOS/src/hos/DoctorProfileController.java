package hos;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DoctorProfileController {
    private String username;
    private String ISA;
    private int loggedInDoctorId;

    @FXML private TableView<Doctor> doctorTableView;
    @FXML private TableColumn<Doctor, Integer> idColumn;
    @FXML private TableColumn<Doctor, Integer> u_idColumn;
    @FXML private TableColumn<Doctor, String> nameColumn;
    @FXML private TableColumn<Doctor, String> lastNameColumn;
    @FXML private TableColumn<Doctor, String> phoneColumn;
    @FXML private TableColumn<Doctor, String> emailColumn;
    @FXML private TableColumn<Doctor, String> specializationColumn;


    @FXML private TextField nameField, lastnameField,phoneField, emailField, specializationField;
    @FXML private Button button_logout, button_goback, updateButton;

    private ObservableList<Doctor> doctorsData = FXCollections.observableArrayList();

    public void setLoggedInDoctorId(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;
        System.out.println("Logged-in patient ID set to: " + loggedInDoctorId);
        loadDoctorData();  // Load patient data as soon as ID is set
    }

    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }

    @FXML
    public void initialize() {
        setupTableColumns();

        button_logout.setOnAction(event -> DBUtils.changeScene0(event, "start.fxml", "HOS", null, null));

        button_goback.setOnAction(event -> {
            System.out.println("Returning to patient_logged-in.fxml with ID: " + loggedInDoctorId);
            DBUtils.changeScene3(event, "doctor_logged-in.fxml", "Welcome", username, ISA, loggedInDoctorId);
        });

        // Ensure data is loaded at the start if ID is already set
        if (loggedInDoctorId > 0) {
            loadDoctorData();
        }
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        u_idColumn.setCellValueFactory(cellData -> cellData.getValue().u_idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
       
        specializationColumn.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());

    }

    private void loadDoctorData() {
    if (loggedInDoctorId == 0) {
        System.out.println("No doctor ID set, skipping data load.");
        return;
    }

    doctorsData.clear();
    String query = "SELECT * FROM doctors WHERE u_id=?";

    try (Connection connection = DBUtils.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, loggedInDoctorId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                Doctor doctor = new Doctor(
                    resultSet.getInt("d_id"),
                    resultSet.getInt("u_id"),
                    resultSet.getString("d_name"),
                    resultSet.getString("d_lastname"),
                    resultSet.getString("d_phone"),
                    resultSet.getString("d_email"),
                    resultSet.getString("d_specialization")
                );
                doctorsData.add(doctor);

                // Auto-fill text fields with the first doctor's details
                nameField.setText(doctor.getName());
                lastnameField.setText(doctor.getLastName());
                phoneField.setText(doctor.getPhone());
                emailField.setText(doctor.getEmail());                
                specializationField.setText(doctor.getSpecialization());
            }
        }
    } catch (SQLException e) {
        showErrorDialog("Database Error", "Unable to load doctor data.", e);
    }

    doctorTableView.setItems(doctorsData);  // Update the table view with the latest data
}

@FXML
private void updateProfile() {
    if (nameField.getText().trim().isEmpty() || lastnameField.getText().trim().isEmpty() ||
        emailField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
        specializationField.getText().trim().isEmpty()) {
        showAlert("Error", "All fields must be filled.");
        return;
    }

    String query = "UPDATE doctors SET d_name=?, d_lastname=?, d_email=?, d_phone=?, d_specialization=? WHERE u_id=?";
    
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, nameField.getText());
        stmt.setString(2, lastnameField.getText());
        stmt.setString(3, emailField.getText());
        stmt.setString(4, phoneField.getText());
        stmt.setString(5, specializationField.getText());
        stmt.setInt(6, loggedInDoctorId);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            showAlert("Success", "Profile updated successfully.");
            loadDoctorData();
        } else {
            showAlert("Error", "Profile update failed.");
        }
    } catch (SQLException e) {
        showErrorDialog("Database Error", "Unable to update doctor profile.", e);
    }
}



private boolean doctorExists() {
    String query = "SELECT COUNT(*) FROM doctors WHERE u_id=?";
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, loggedInDoctorId);
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("Doctor exists: " + (count > 0));  // Add logging here
                return count > 0; // If count > 0, doctor exists
            }
        }
    } catch (SQLException e) {
        showErrorDialog("Database Error", "Unable to check if doctor exists.", e);
    }
    return false;
}







    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
