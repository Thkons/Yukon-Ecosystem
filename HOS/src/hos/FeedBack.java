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
public class FeedBack {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty subject;
    private final SimpleStringProperty feed_back;
    private final SimpleStringProperty message;

    public FeedBack(int id, String name, String email, String subject, String feed_back, String message) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.subject = new SimpleStringProperty(subject);
        this.feed_back = new SimpleStringProperty(feed_back);
        this.message = new SimpleStringProperty(message);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }
    
    public String getFeedBack() {
        return feed_back.get();
    }

    public SimpleStringProperty feedBackProperty() {
        return feed_back;
    }
    
    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }
}