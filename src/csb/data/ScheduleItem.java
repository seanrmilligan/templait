package csb.data;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author McKillaGorilla
 */
public class ScheduleItem implements Comparable {
    final StringProperty description;
    final ObjectProperty<LocalDate> date;
    final StringProperty link;
    public static final String DEFAULT_DESCRIPTION = "<ENTER DESCRIPTION>";
    public static final String DEFAULT_URL = "http://www.google.com";    
    
    public ScheduleItem() {
        description = new SimpleStringProperty(DEFAULT_DESCRIPTION);
        date = new SimpleObjectProperty(LocalDate.now());
        link = new SimpleStringProperty(DEFAULT_URL);
    }
    
    public void reset() {
        setDescription(DEFAULT_DESCRIPTION);
        setDate(LocalDate.now());
        setLink(DEFAULT_URL);
    }
    
    public String getDescription() {
        return description.get();
    }
    
    public void setDescription(String initDescription) {
        description.set(initDescription);
    }
    
    public StringProperty descriptionProperty() {
        return description;
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
   
    public String getLink() {
        return link.get();
    }
    
    public void setLink(String initLink) {
        link.set(initLink);
    }
    
    public StringProperty linkProperty() {
        return link;
    }    
    
    @Override
    public int compareTo(Object obj) {
        ScheduleItem otherItem = (ScheduleItem)obj;
        return getDate().compareTo(otherItem.getDate());
    }
}
