package hos;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class PatientProfileController {
    private String username;
    private String ISA;
    private int loggedInPatientId;

    @FXML private TableView<Patient> patientTableView;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, Integer> u_idColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> lastNameColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;
    @FXML private TableColumn<Patient, String> symptomsColumn;
    @FXML private TableColumn<Patient, String> treatmentColumn;
    @FXML private TableColumn<Patient, String> dobColumn;

    @FXML private TextField nameField, lastnameField, emailField, phoneField, symptomsField;
    @FXML private DatePicker dob;  // DatePicker field
    @FXML private Button button_logout, button_goback, updateButton;

    private ObservableList<Patient> patientsData = FXCollections.observableArrayList();

    public void setLoggedInPatientId(int loggedInPatientId) {
        this.loggedInPatientId = loggedInPatientId;
        System.out.println("Logged-in patient ID set to: " + loggedInPatientId);
        loadPatientData();  // Load patient data as soon as ID is set
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
            System.out.println("Returning to patient_logged-in.fxml with ID: " + loggedInPatientId);
            DBUtils.changeScene5(event, "patient_logged-in.fxml", "Welcome", username, ISA, loggedInPatientId);
        });

        // Ensure data is loaded at the start if ID is already set
        if (loggedInPatientId > 0) {
            loadPatientData();
        }
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        u_idColumn.setCellValueFactory(cellData -> cellData.getValue().u_idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        symptomsColumn.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());
        treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
        dobColumn.setCellValueFactory(cellData -> cellData.getValue().dobProperty());
    }

    private void loadPatientData() {
        if (loggedInPatientId == 0) {
            System.out.println("No patient ID set, skipping data load.");
            return;
        }
        
        patientsData.clear();
        String query = "SELECT * FROM patients WHERE u_id=?";
        
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedInPatientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Patient patient = new Patient(
                        resultSet.getInt("p_id"),
                        resultSet.getInt("u_id"),
                        resultSet.getString("p_name"),
                        resultSet.getString("p_lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("p_symptoms"),
                        resultSet.getString("p_treatment"),
                        resultSet.getString("dob")
                    );
                    patientsData.add(patient);

                    // Auto-fill the text fields with the first patient's details
                    nameField.setText(patient.getName());
                    lastnameField.setText(patient.getLastName());
                    emailField.setText(patient.getEmail());
                    phoneField.setText(patient.getPhone());
                    symptomsField.setText(patient.getSymptoms());
                    dob.setValue(LocalDate.parse(patient.getDob()));  // Set DatePicker value with LocalDate
                }
            }
        } catch (SQLException e) {
            showErrorDialog("Database Error", "Unable to load patient data.", e);
        }

        patientTableView.setItems(patientsData);
    }

    @FXML
    private void updateProfile() {
        if (nameField.getText().trim().isEmpty() || lastnameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() ||
            symptomsField.getText().trim().isEmpty() || dob.getValue() == null) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        String query = "UPDATE patients SET p_name=?, p_lastname=?, email=?, phone=?, p_symptoms=?, dob=? WHERE u_id=?";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nameField.getText());
            stmt.setString(2, lastnameField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, phoneField.getText());
            stmt.setString(5, symptomsField.getText());
            stmt.setString(6, dob.getValue().toString());  // Get LocalDate from DatePicker and convert to String
            stmt.setInt(7, loggedInPatientId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Profile updated successfully.");
                loadPatientData();
            } else {
                showAlert("Error", "Profile update failed.");
            }
        } catch (SQLException e) {
            showErrorDialog("Database Error", "Unable to update patient profile.", e);
        }
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
