package hos;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

public class PatientFilesController {

    private String username;
    private String ISA;
    private int loggedInDoctorId;
    
    @FXML
    private Button button_logout; 
    
    @FXML 
    private Button button_goback;
    
    @FXML 
    private Button button_patients;

    @FXML
    private TableView<Files> filesTable;
    @FXML
    private TableColumn<Files, Integer> fileId;
    @FXML
    private TableColumn<Files, Integer> patientId;
    @FXML
    private TableColumn<Files, String> fileName;
    @FXML
    private TableColumn<Files, String> fileType;
    @FXML
    private TableColumn<Files, String> uploadDate;
    @FXML
    private TableColumn<Files, String> downloadColumn;

    private ObservableList<Files> filesList = FXCollections.observableArrayList();
    
    public void setLoggedInDoctorId(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;
        System.out.println("Logged-in doctor ID set to: " + loggedInDoctorId);
    }

    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
    }

    @FXML
    public void initialize() {
        // Setting up the columns
        fileId.setCellValueFactory(new PropertyValueFactory<>("fileId"));
        patientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileType.setCellValueFactory(new PropertyValueFactory<>("fileType"));
        uploadDate.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));

        // Add data to the TableView
        filesTable.setItems(filesList);

        // Add data from the database
        fetchDataFromDatabase();

        // Adding download button action
        downloadColumn.setCellFactory(column -> {
            TableCell<Files, String> cell = new TableCell<Files, String>() {
                private final Button downloadButton = new Button("Download");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(downloadButton);
                        setText(null);

                        downloadButton.setOnMouseClicked(event -> {
                            Files file = getTableView().getItems().get(getIndex());
                            String filePath = file.getFilePath();
                            downloadFile(filePath);
                        });
                    }
                }
            };
            return cell;
        });

        button_logout.setOnAction(event -> DBUtils.changeScene0(event, "start.fxml", "HOS", null, null));
        button_goback.setOnAction(event -> DBUtils.changeScene3(event, "doctor_logged-in.fxml", "Welcome", username, ISA, loggedInDoctorId));
        button_patients.setOnAction(event -> DBUtils.changeScene2(event, "patients-list-view.fxml", "Welcome", null, null));
    }

    // Fetch data from the database using DBUtils
    private void fetchDataFromDatabase() {
        String query = "SELECT * FROM patient_files"; // Your SQL query to fetch files
        try (Connection conn = DBUtils.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int fileId = rs.getInt("file_id");
                int patientId = rs.getInt("patient_id");
                String fileName = rs.getString("file_name");
                String fileType = rs.getString("file_type");
                String uploadDate = rs.getString("uploaded_at");
                String filePath = rs.getString("file_path");

                // Create Files object and add to the list
                Files file = new Files(fileId, patientId, fileName, fileType, uploadDate, filePath);
                filesList.add(file);
            }
        } catch (Exception e) {
            System.out.println("Error fetching data from database: " + e.getMessage());
        }
    }

    // Download file logic
   private void downloadFile(String filePath) {
    String userHome = System.getProperty("user.home");
    String targetDirectory = userHome + "/Downloads/";

    File sourceFile = new File(filePath);
    File targetFile = new File(targetDirectory + sourceFile.getName());

    if (!sourceFile.exists()) {
        System.out.println("Error: Source file does not exist!");
        return;
    }

    try {
        java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File downloaded to: " + targetFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error downloading file: " + e.getMessage());
    }
}

}
