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
public class Applicant{

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty ISA;

    public Applicant(int id, String username, String password, String ISA) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.ISA = new SimpleStringProperty(ISA);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getISA() {
        return ISA.get();
    }

    public SimpleStringProperty ISAProperty() {
        return ISA;
    }
}