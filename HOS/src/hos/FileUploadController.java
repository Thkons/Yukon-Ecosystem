package hos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUploadController {
    private String username;
    private String ISA;
    private static final String UPLOAD_DIR = "uploads/";
    private int loggedInPatientId;
    @FXML private Button button_logout, button_goback;

    @FXML private TableView<FileRecord> filesTableView;
    @FXML private TableColumn<FileRecord, String> fileNameColumn;
    @FXML private TableColumn<FileRecord, String> fileTypeColumn;
    @FXML private TableColumn<FileRecord, String> uploadDateColumn;
    @FXML private Button deleteFileButton;

    private ObservableList<FileRecord> filesData = FXCollections.observableArrayList();

    public void setLoggedInPatientId(int id) {
        this.loggedInPatientId = id;
        loadUploadedFiles();
    }
    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }

    @FXML
    private void initialize() {
        setupTableColumns();
        deleteFileButton.setDisable(true);
        
        button_logout.setOnAction(event -> DBUtils.changeScene0(event, "start.fxml", "HOS", null, null));

        button_goback.setOnAction(event -> {
            System.out.println("Returning to patient_logged-in.fxml with ID: " + loggedInPatientId);
            DBUtils.changeScene5(event, "patient_logged-in.fxml", "Welcome", username, ISA, loggedInPatientId);
        });

        filesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteFileButton.setDisable(newSelection == null);
        });

        if (loggedInPatientId > 0) {
            loadUploadedFiles();
        }
    }

    private void setupTableColumns() {
        fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        fileTypeColumn.setCellValueFactory(cellData -> cellData.getValue().fileTypeProperty());
        uploadDateColumn.setCellValueFactory(cellData -> cellData.getValue().uploadDateProperty());
    }

    @FXML
    private void uploadExam() {
        uploadFile("Examination");
    }

    @FXML
    private void uploadTreatmentPlan() {
        uploadFile("Treatment");
    }

    private void uploadFile(String type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            ensureUploadDirExists();

            String fileName = selectedFile.getName();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String newFileName = timestamp + "_" + fileName;

            File destFile = new File(UPLOAD_DIR + newFileName);
            String filePath = destFile.getAbsolutePath();

            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                saveFileToDatabase(newFileName, filePath, type);
                loadUploadedFiles(); // Refresh table after upload
            } catch (IOException e) {
                showAlert("Error", "File upload failed.");
                e.printStackTrace();
            }
        }
    }

    private void ensureUploadDirExists() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    private void saveFileToDatabase(String fileName, String filePath, String type) {
        if (loggedInPatientId == 0) {
            showAlert("Error", "No patient is logged in!");
            return;
        }

        String query = "INSERT INTO patient_files (patient_id, file_type, file_name, file_path, uploaded_at) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, loggedInPatientId);
            stmt.setString(2, type);
            stmt.setString(3, fileName);
            stmt.setString(4, filePath);
            stmt.setString(5, LocalDateTime.now().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Error", "Could not save file.");
            e.printStackTrace();
        }
    }

    private void loadUploadedFiles() {
        if (loggedInPatientId == 0) {
            return;
        }

        filesData.clear();
        String query = "SELECT file_id, file_type, file_name, uploaded_at FROM patient_files WHERE patient_id=?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, loggedInPatientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                filesData.add(new FileRecord(
                    rs.getInt("file_id"),   
                    rs.getString("file_name"),
                    rs.getString("file_type"),
                    rs.getString("uploaded_at")
                ));
            }

        } catch (SQLException e) {
            showAlert("Error", "Could not load uploaded files.");
            e.printStackTrace();
        }

        filesTableView.setItems(filesData);
    }

    @FXML
    private void deleteSelectedFile() {
        FileRecord selectedFile = filesTableView.getSelectionModel().getSelectedItem();
        if (selectedFile == null) {
            showAlert("Error", "No file selected.");
            return;
        }

        String query = "DELETE FROM patient_files WHERE file_id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, selectedFile.getFileId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                File file = new File(UPLOAD_DIR + selectedFile.getFileName());
                if (file.exists()) {
                    file.delete();
                }

                showAlert("Success", "File deleted successfully.");
                loadUploadedFiles(); // Refresh table
            } else {
                showAlert("Error", "File deletion failed.");
            }

        } catch (SQLException e) {
            showAlert("Error", "Could not delete file.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
