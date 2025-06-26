/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author Thanasis
 */
public class DBUtils {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    
    
    public static void changeScene0(ActionEvent event, String fxmlFile, String title, String username, String ISA)
    {
        Parent root = null;
        
        if (username != null && ISA !=null)
        {
            try {
                FXMLLoader loader = new FXMLLoader (DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }else {
            try
            {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();    
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 750));
        stage.show();
        
                }

    public static void changeScene1(ActionEvent event, String fxmlFile, String title, String username, String ISA)
    {
        Parent root = null;
        
        if (username != null && ISA !=null)
        {
            try {
                FXMLLoader loader = new FXMLLoader (DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                
                if (fxmlFile.equals("logged-in_medical-secretary.fxml")){
                MedicalSecretaryLoggedInController medicalSecretaryLoggedInController = loader.getController();
                medicalSecretaryLoggedInController.setUserInformation(username,ISA);
                }
                if (fxmlFile.equals("doctors_list.fxml")){
                DoctorsListController doctorsListController = loader.getController();
                doctorsListController.setUserInformation(username,ISA);
                }
                if (fxmlFile.equals("patients_list.fxml")){
                PatientsListController patientsListController = loader.getController();
                patientsListController.setUserInformation(username,ISA);
                }
                if (fxmlFile.equals("users_list.fxml")){
                UsersListController usersListController = loader.getController();
                usersListController.setUserInformation(username,ISA);
                }
                if (fxmlFile.equals("appointments_list.fxml")){
                AppointmentsListController appointmentsListController = loader.getController();
                appointmentsListController.setUserInformation(username,ISA);
                }
                if (fxmlFile.equals("appointments_record.fxml")){
                AppointmentsRecordController appointmentsRecordController = loader.getController();
                appointmentsRecordController.setUserInformation(username,ISA);
                }
                if (fxmlFile.equals("appointment_time_management.fxml")){
                AppointmentTimeManagerController appointmentTimeManagerController = loader.getController();
                appointmentTimeManagerController.setUserInformation(username,ISA);
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }else {
            try
            {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();    
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 750));
        stage.show();
        }
    
    public static void changeScene2(ActionEvent event, String fxmlFile, String title, String username, String ISA)
    {
        Parent root = null;
        
        if (username != null && ISA !=null)
        {
            try {
                FXMLLoader loader = new FXMLLoader (DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }else {
            try
            {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Stage stage = new Stage();  
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800, 550));
        stage.show();
        
                }
    
     public static void changeScene3(ActionEvent event, String fxmlFile, String title, String username, String ISA, int loggedInDoctorId) {
    Parent root = null;
    
    System.out.println("Navigating to scene: " + fxmlFile);
    System.out.println("Passing doctor ID: " + loggedInDoctorId);  // Debug print

    try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        root = loader.load();
        
        // If navigating to patient_logged-in.fxml
        if (fxmlFile.equals("doctor_logged-in.fxml")) {
            DoctorLoggedInController loggedInController = loader.getController();
            loggedInController.setUserInformation(username, ISA);
            loggedInController.setLoggedInDoctorId(loggedInDoctorId);
        }

        // If navigating to appointment.fxml
        if (fxmlFile.equals("doctor_appointments.fxml")) {
            DoctorAppointmentsController doctorAppointmentsController = loader.getController();
            doctorAppointmentsController.setLoggedInDoctorId(loggedInDoctorId);  // Pass the patient ID here
            doctorAppointmentsController.setUserInformation(username, ISA);

            
        }
        if (fxmlFile.equals("doctor_view_appointment.fxml")) {
            DoctorViewAppointmentController doctorViewAppointmentController = loader.getController();
            doctorViewAppointmentController.setLoggedInDoctorId(loggedInDoctorId);  // Pass the patient ID here
            doctorViewAppointmentController.setUserInformation(username, ISA);
        }
  
        if (fxmlFile.equals("d_patients_list.fxml")) {
            DPatientsListController dPatientsListController = loader.getController();
            dPatientsListController.setLoggedInDoctorId(loggedInDoctorId);
            dPatientsListController.setUserInformation(username, ISA);
            // Pass the patient ID here
        }
        if (fxmlFile.equals("d_doctors_list.fxml")) {
            DDoctorsListController dDoctorsListController = loader.getController();
            dDoctorsListController.setLoggedInDoctorId(loggedInDoctorId);  // Pass the patient ID here
            dDoctorsListController.setUserInformation(username, ISA);
        }
        if (fxmlFile.equals("doctor_past_appointments.fxml")) {
            DoctorPastAppointmentsController doctorPastAppointmentsController = loader.getController();
            doctorPastAppointmentsController.setLoggedInDoctorId(loggedInDoctorId);  // Pass the patient ID here
            doctorPastAppointmentsController.setUserInformation(username, ISA);
        }
        if (fxmlFile.equals("doctor_profile.fxml")) {
            DoctorProfileController doctorProfileController = loader.getController();
            doctorProfileController.setLoggedInDoctorId(loggedInDoctorId);  // Pass the patient ID here
            doctorProfileController.setUserInformation(username, ISA);
        }
        if (fxmlFile.equals("patient_files.fxml")) {
            PatientFilesController patientFilesController = loader.getController();
            patientFilesController.setLoggedInDoctorId(loggedInDoctorId);  // Pass the patient ID here
            patientFilesController.setUserInformation(username, ISA);
        }
       

    } catch (IOException e) {
        e.printStackTrace();
    }

    // Set up the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(new Scene(root, 1000, 750));
    stage.show();
}
     
      public static void changeScene4(ActionEvent event, String fxmlFile, String title, String username, String ISA)
    {
        Parent root = null;
        
        if (username != null && ISA !=null)
        {
            try {
                FXMLLoader loader = new FXMLLoader (DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                thankYouController thankyouController = loader.getController();
                thankyouController.setUserInformation(username,ISA);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }else {
            try
            {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();    
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 750));
        stage.show();
        
                }
      
 public static void changeScene5(ActionEvent event, String fxmlFile, String title, String username, String ISA, int loggedInPatientId) {
    Parent root = null;
    
    System.out.println("Navigating to scene: " + fxmlFile);
    System.out.println("Passing patient ID: " + loggedInPatientId);  // Debug print

    try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        root = loader.load();
        
        
        
        // If navigating to patient_logged-in.fxml
        if (fxmlFile.equals("patient_logged-in.fxml")) {
            PatientLoggedInController loggedInController = loader.getController();
            loggedInController.setUserInformation(username, ISA);
            loggedInController.setLoggedInPatientId(loggedInPatientId);
        }

        // If navigating to appointment.fxml
        if (fxmlFile.equals("appointment.fxml")) {
            AppointmentController appointmentController = loader.getController();
            appointmentController.setLoggedInPatientId(loggedInPatientId); 
            appointmentController.setUserInformation(username, ISA);// Pass the patient ID here
        }
        
        // If navigation to view_appointments.fxml
        if(fxmlFile.equals("view_appointment.fxml")) {
            ViewAppointmentController viewAppointmentController = loader.getController();
            viewAppointmentController.setLoggedInPatientId(loggedInPatientId);
            viewAppointmentController.setUserInformation(username, ISA);
        }
         if(fxmlFile.equals("patient_profile.fxml")) {
            PatientProfileController patientProfileController = loader.getController();
            patientProfileController.setLoggedInPatientId(loggedInPatientId);
            System.out.println("Patient ID passed: " + loggedInPatientId);
            patientProfileController.setUserInformation(username, ISA);
        }
         if(fxmlFile.equals("patient_past_appointments.fxml")) {
            PatientPastAppointmentsController patientPastAppointmentsController = loader.getController();
            patientPastAppointmentsController.setLoggedInPatientId(loggedInPatientId);
            System.out.println("Patient ID passed: " + loggedInPatientId);
            patientPastAppointmentsController.setUserInformation(username, ISA);
        }
         if(fxmlFile.equals("file_upload.fxml")) {
            FileUploadController fileUploadController = loader.getController();
            fileUploadController.setLoggedInPatientId(loggedInPatientId);
            System.out.println("Patient ID passed: " + loggedInPatientId);
            fileUploadController.setUserInformation(username, ISA);
        }
        

    } catch (IOException e) {
        e.printStackTrace();
    }

    // Set up the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(new Scene(root, 1000, 750));
    stage.show();
}


    


      
      public static void changeScene6(ActionEvent event, String fxmlFile, String title, String username, String ISA, int loggedInSupportTeamId)
    {
        Parent root = null;
        
        if (username != null && ISA !=null)
        {
            try {
                FXMLLoader loader = new FXMLLoader (DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                
                if (fxmlFile.equals("logged-in_support-team.fxml")) {
            SupportTeamLoggedInController supportTeamLoggedInController = loader.getController();
            supportTeamLoggedInController.setLoggedInSupportTeamId(loggedInSupportTeamId);
            supportTeamLoggedInController.setUserInformation(username,ISA);
        }
                
                if (fxmlFile.equals("feedBackList.fxml")) {
            FeedBackListController feedBackListController = loader.getController();
            feedBackListController.setLoggedInSupportTeamId(loggedInSupportTeamId);
            feedBackListController.setUserInformation(username,ISA);
        }
                
                if (fxmlFile.equals("reports_list.fxml")) {
            ReportsListController reportsListController = loader.getController();
            reportsListController.setLoggedInSupportTeamId(loggedInSupportTeamId);
            reportsListController.setUserInformation(username,ISA);
        }
                
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }else {
            try
            {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();    
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 750));
        stage.show();
        }
      
     



    
    
    
    
    
   
    
   
    
   
    
    
    
    
     public static void changeScene7(ActionEvent event, String fxmlFile, String title, String username, String u_id) {
    Parent root = null;

    try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        root = loader.load();

        // Get the controller of the new scene
        MakePatientController controller = loader.getController();
        controller.setUserId(Integer.parseInt(u_id));  // Set the u_id in MakePatientController
        

    } catch (IOException e) {
        e.printStackTrace();
    }

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(new Scene(root, 1000, 750));
    stage.show();
}

     
    
    
     
    
    
    
    
    
public static void signUpUser(ActionEvent event, String username, String password, String repeat_password, String ISA) {
    if (!password.equals(repeat_password)) {
        showAlert("Error", "Passwords do not match.", Alert.AlertType.ERROR);
        return;
    }

    String queryCheckUser = "SELECT * FROM users WHERE username = ?";
    String queryInsertUser = "INSERT INTO users (username, password, ISA) VALUES (?, ?, ?)";
    String queryInsertApplicant = "INSERT INTO applicants (apl_username, apl_password, apl_ISA) VALUES (?, ?, ?)";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
         PreparedStatement psCheckUserExists = connection.prepareStatement(queryCheckUser)) {

        // Check if username already exists
        psCheckUserExists.setString(1, username);
        try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
            if (resultSet.isBeforeFirst()) {
                showAlert("Error", "Username already exists.", Alert.AlertType.ERROR);
                return;
            }
        }

        // Decide which table to insert into
        String insertQuery = "Patient".equals(ISA) ? queryInsertUser : queryInsertApplicant;

        try (PreparedStatement psInsert = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            psInsert.setString(1, username);
            psInsert.setString(2, password);
            psInsert.setString(3, ISA);
            int rowsAffected = psInsert.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int u_id = generatedKeys.getInt(1);
                        System.out.println("User created with u_id: " + u_id);

                        if ("Patient".equals(ISA)) {
                            DBUtils.changeScene7(event, "make_patient.fxml", "Create Patient Profile", username, String.valueOf(u_id));
                        } else {
                            DBUtils.changeScene4(event, "thankYou.fxml", "Thank You", null, null);
                        }
                    }
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
    }
}

// Helper method for alerts
private static void showAlert(String title, String message, Alert.AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.show();
}








    public static void logInUser1(ActionEvent event, String username, String password)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                        preparedStatement = connection.prepareStatement("SELECT password, ISA FROM users WHERE username=? AND ISA='Medical Secretary'");
                        preparedStatement.setString(1,username);
                        resultSet = preparedStatement.executeQuery();
                        
                        if (!resultSet.isBeforeFirst())
                        {
                            System.out.println("User not found in the database");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Provided credential are incorrect");
                            alert.show();
                        }else 
                        {
                            while (resultSet.next()){
                                String retrievedPassword = resultSet.getString("password");
                                String retrievedISA = resultSet.getString("ISA");
                               if (retrievedPassword.equals(password))
                               {
                                   changeScene1(event, "logged-in_medical-secretary.fxml", "Welcome", username, retrievedISA);
                               } else{
                                   System.out.println("Passwords did  not match");
                                   Alert alert = new Alert(Alert.AlertType.ERROR);
                                   alert.setContentText("The provided credentials are incorrect");
                                   alert.show();
                               }
                            }
                            
                        }
                           }catch (SQLException e)
                           {
                               e.printStackTrace();
                           }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void logInUser2(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            preparedStatement = connection.prepareStatement("SELECT password, ISA, u_id FROM users WHERE username=? AND ISA='Doctor'");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedISA = resultSet.getString("ISA");
                    int doctorId = resultSet.getInt("u_id");

                    System.out.println("Logged-in user ID (u_id): " + doctorId);

                    if (retrievedPassword.equals(password)) {
                        // Pass the patient ID to the next screen
                        changeScene3(event, "doctor_logged-in.fxml", "Welcome", username, retrievedISA, doctorId);
                    } else {
                        System.out.println("Passwords did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void logInUser3(ActionEvent event, String username, String password)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                        preparedStatement = connection.prepareStatement("SELECT password, ISA, u_id FROM users WHERE username=? AND ISA='Support Team Member'");
                        preparedStatement.setString(1,username);
                        resultSet = preparedStatement.executeQuery();
                        
                        if (!resultSet.isBeforeFirst())
                        {
                            System.out.println("User not found in the database");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Provided credential are incorrect");
                            alert.show();
                        }else 
                        {
                            while (resultSet.next()){
                                String retrievedPassword = resultSet.getString("password");
                                String retrievedISA = resultSet.getString("ISA");
                                int supportTeamId = resultSet.getInt("u_id");

                    System.out.println("Logged-in user ID (u_id): " + supportTeamId);
                               if (retrievedPassword.equals(password))
                               {
                                   changeScene6(event, "logged-in_support-team.fxml", "Welcome", username, retrievedISA, supportTeamId);
                               } else{
                                   System.out.println("Passwords did  not match");
                                   Alert alert = new Alert(Alert.AlertType.ERROR);
                                   alert.setContentText("The provided credentials are incorrect");
                                   alert.show();
                               }
                            }
                            
                        }
                           }catch (SQLException e)
                           {
                               e.printStackTrace();
                           }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
public static void logInUser4(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            preparedStatement = connection.prepareStatement("SELECT password, ISA, u_id FROM users WHERE username=? AND ISA='Patient'");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedISA = resultSet.getString("ISA");
                    int patientId = resultSet.getInt("u_id");

                    System.out.println("Logged-in user ID (u_id): " + patientId);

                    if (retrievedPassword.equals(password)) {
                        // Pass the patient ID to the next screen
                        changeScene5(event, "patient_logged-in.fxml", "Welcome", username, retrievedISA, patientId);
                    } else {
                        System.out.println("Passwords did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



     
     
     
  public static Connection getConnection() throws SQLException {
        // Your connection code here, like:
        // DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "user", "password");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
    } 

   

}
     
    

   
   
