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
public class Report {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty m_id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty problem;
     private final SimpleStringProperty report;

    public Report(int id, int m_id, String name, String lastName, String problem, String report) {
        this.id = new SimpleIntegerProperty(id);
        this.m_id = new SimpleIntegerProperty(m_id);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.problem = new SimpleStringProperty(problem);
        this.report = new SimpleStringProperty(report);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }
    
    public int getM_Id() {
        return m_id.get();
    }

    public SimpleIntegerProperty m_idProperty() {
        return m_id;
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

    public String getProblem() {
        return problem.get();
    }

    public SimpleStringProperty problemProperty() {
        return problem;
    }
    
    public String getReport() {
        return report.get();
    }

    public SimpleStringProperty reportProperty() {
        return report;
    }
}