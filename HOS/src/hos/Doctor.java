/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Thanasis
 */
public class Doctor {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty u_id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty specialization;

    public Doctor(int id, int u_id, String name, String lastName, String phone, String email, String specialization) {
        this.id = new SimpleIntegerProperty(id);
        this.u_id = new SimpleIntegerProperty(u_id);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.specialization = new SimpleStringProperty(specialization);
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
    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }
    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getSpecialization() {
        return specialization.get();
    }

    public SimpleStringProperty specializationProperty() {
        return specialization;
    }
}