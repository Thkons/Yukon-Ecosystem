package hos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PatientLoggedInController {

    private String username;
    private String ISA;
    private int loggedInPatientId;

   @FXML 
private Label label_welcome;
    
    @FXML
    private Button button_bookAppointment;
    
    @FXML
    private Button button_PastAppointments;

    @FXML
    private Button button_viewAppointments;
    
    @FXML
    private Button button_UploadFile;
    
    @FXML
    private Button button_PatientProfile;

    @FXML
    private Button button_logout;

    // Method to set user information (like username, ISA)
    public void setUserInformation(String username, String ISA) {
        this.username = username;
        this.ISA = ISA;
        label_welcome.setText("WELCOME TO HOS "+  username);
    }

    // Method to set logged-in patient ID
    public void setLoggedInPatientId(int loggedInPatientId) {
        this.loggedInPatientId = loggedInPatientId;
        System.out.println("Logged-in patient ID set to: " + loggedInPatientId); // Debugging
    }

    @FXML
    public void initialize() {
        // Set up event handlers for the buttons
        button_bookAppointment.setOnAction(event -> {
            System.out.println("Book Appointment clicked"); // Debugging
            // Pass the logged-in patient ID when navigating to the appointment screen
            DBUtils.changeScene5(event, "appointment.fxml", "Book Appointment", username, ISA, loggedInPatientId);
        });

        button_viewAppointments.setOnAction(event -> {
            System.out.println("View Appointments clicked"); // Debugging
            DBUtils.changeScene5(event, "view_appointment.fxml", "View Your Appointments", username, ISA, loggedInPatientId);
        });
        button_PatientProfile.setOnAction(event -> {
            System.out.println("View Patients Profile clicked"); // Debugging
            DBUtils.changeScene5(event, "patient_profile.fxml", "Patient Profile", username, ISA, loggedInPatientId);
        });
        button_UploadFile.setOnAction(event -> {
            
            DBUtils.changeScene5(event, "file_upload.fxml", "File Upload", username, ISA, loggedInPatientId);
        });
        
        button_PastAppointments.setOnAction(event -> {
            
            DBUtils.changeScene5(event, "patient_past_appointments.fxml", "Past Appointments", username, ISA, loggedInPatientId);
        });
        
        

        button_logout.setOnAction(event -> {
            System.out.println("Logout clicked"); // Debugging
            DBUtils.changeScene0(event, "start.fxml", "HOS", null, null);
        });
    }
}
