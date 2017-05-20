/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csb.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author McKillaGorilla
 */
public class Lecture {
    final StringProperty topic;
    final IntegerProperty sessions;
    public static final String DEFAULT_TOPIC = "<ENTER TOPIC>";
    public static final int DEFAULT_SESSIONS = 1;    
    
    public Lecture() {
        topic = new SimpleStringProperty(DEFAULT_TOPIC);
        sessions = new SimpleIntegerProperty(DEFAULT_SESSIONS);
    }
    
    public void reset() {
        setTopic(DEFAULT_TOPIC);
        setSessions(DEFAULT_SESSIONS);
    }
    
    public String getTopic() {
        return topic.get();
    }
    
    public void setTopic(String initTopic) {
        topic.set(initTopic);
    }
    
    public StringProperty topicProperty() {
        return topic;
    }
    
    public int getSessions() {
        return sessions.get();
    }
    
    public void setSessions(int initSessions) {
        sessions.set(initSessions);
    }
    
    public IntegerProperty sessionsProperty() {
        return sessions;
    }
}
