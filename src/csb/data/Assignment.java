package csb.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author McKillaGorilla
 */
public class Assignment {
    final StringProperty name;
    final StringProperty topics;
    final ObjectProperty<LocalDate> date;
    public static final String DEFAULT_NAME = "<ENTER NAME>";
    public static final String DEFAULT_TOPICS = "<ENTER TOPICS>";
    
    public Assignment() {
        name = new SimpleStringProperty(DEFAULT_NAME);
        topics = new SimpleStringProperty(DEFAULT_NAME);
        date = new SimpleObjectProperty(LocalDate.now());
    }
    
    public void reset() {
        setName(DEFAULT_NAME);
        setTopics(DEFAULT_TOPICS);
        setDate(LocalDate.now());
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String initName) {
        name.set(initName);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public LocalDate getDate() {
        return (LocalDate)date.get();
    }
    
    public void setDate(LocalDate initDate) {
        date.set(initDate);
    }
    
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
   
    public String getTopics() {
        return topics.get();
    }
    
    public void setTopics(String initTopics) {
        topics.set(initTopics);
    }
    
    public StringProperty topicsProperty() {
        return topics;
    }
}