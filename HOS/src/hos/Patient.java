package hos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Patient {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty u_id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty symptoms;
    private final SimpleStringProperty treatment;
    private final SimpleStringProperty dob;


    public Patient(int id, int u_id, String name, String lastName, String email, String phone, String symptoms, String treatment, String dob) {
        this.id = new SimpleIntegerProperty(id);
        this.u_id = new SimpleIntegerProperty(u_id);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.symptoms = new SimpleStringProperty(symptoms);
        this.treatment = new SimpleStringProperty(treatment);
        this.dob = new SimpleStringProperty(dob);
        
        
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }
    
    public int getU_Id() {
        return u_id.get();
    }

    public SimpleIntegerProperty u_idProperty() {
        return u_id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }
    
    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }
    
    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public String getSymptoms() {
        return symptoms.get();
    }

    public SimpleStringProperty symptomsProperty() {
        return symptoms;
    }

    // Added setSymptoms method
    public void setSymptoms(String symptoms) {
        this.symptoms.set(symptoms);
    }
    
    public String getTreatment() {
        return treatment.get();
    }

    public SimpleStringProperty treatmentProperty() {
        return treatment;
    }

    // Added setSymptoms method
    public void settreatment(String treatment) {
        this.treatment.set(treatment);
    }
    
     public String getDob() {
        return dob.get();
    }

    public SimpleStringProperty dobProperty() {
        return dob;
    }
    
    
    
   
}
