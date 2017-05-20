package csb.error;

import static csb.CSB_PropertyType.END_DATE_NOT_A_FRIDAY_ERROR_MESSAGE;
import static csb.CSB_PropertyType.START_DATE_AFTER_END_DATE_ERROR_MESSAGE;
import static csb.CSB_PropertyType.START_DATE_NOT_A_MONDAY_ERROR_MESSAGE;
import static csb.CSB_StartupConstants.CLOSE_BUTTON_LABEL;
import static csb.CSB_StartupConstants.PROPERTIES_FILE_ERROR_MESSAGE;
import csb.data.Course;
import csb.gui.MessageDialog;
import javafx.stage.Stage;
import com.seanrmilligan.properties_manager.PropertiesManager;

/**
 * This class provides a response for every type of error we anticipate
 * inside of our program. It's convenient to do this in one place so
 * that we have a common format for dealing with problems.
 * 
 * @author Richard McKenna
 */
public class ErrorHandler {
    // THIS CLASS USES A SINGLETON DESIGN PATTER, WHICH IS CONVENIENT
    // BECAUSE IT NEEDS TO BE USED BY SO MANY OTHER CLASSES
    static ErrorHandler singleton;
    
    // WE'LL MAKE USE OF THIS DIALOG TO PROVIDE OUR MESSAGE FEEDBACK
    MessageDialog messageDialog;
    
    // THE PROPERTIES MANAGER WILL GIVE US THE TEXT TO DISPLAY
    PropertiesManager properties;

    /**
     * Note that this constructor is private and so can never be called
     * outside of this class.
     */
    private ErrorHandler() {
        // THIS HELPS US KEEP TRACK OF WHETHER WE NEED TO
        // CONSTRUCT THE SINGLETON OR NOT EACH TIME IT'S ACCESSED
        singleton = null;
        
        // WE ONLY NEED TO GET THE SINGLETON ONCE
        properties = PropertiesManager.getPropertiesManager();
    }
    
    /**
     * This method initializes this error handler's message dialog
     * so that it may provide feedback when errors occur.
     * 
     * @param owner The parent window for the modal message dialog.
     */
    public void initMessageDialog(Stage owner) {
        // WE'LL USE THIS DIALOG TO PROVIDE FEEDBACK WHEN ERRORS OCCUR
        messageDialog = new MessageDialog(owner, CLOSE_BUTTON_LABEL);        
    }

    /**
     * Accessor method for getting this singleton.
     * 
     * @return The singleton ErrorHandler used by the entire
     * application for responding to error conditions.
     */
    public static ErrorHandler getErrorHandler() {
        // INITIALIZE THE SINGLETON ONLY THE FIRST TIME
        if (singleton == null)
            singleton = new ErrorHandler();
        
        // BUT ALWAYS RETURN IT
        return singleton;
    }
    
    public void handleNewCourseError() {
        
    }
    
    public void handleLoadCourseError() {
        
    }
    
    public void handleSaveCourseError() {
        
    }

    public void handleViewSchedulePageError(String pageURL) {
        
    }
     
    public void handleExportCourseError(Course courseBeingExported) {
        
    }
        
    public void handleExitError() {
        
    }

    public void handleUpdateCourseError() {
        
    }

    /**
     * This function provides feedback to the user when the calendar
     * start date is not a Monday, which is forbidden.
     */
    public void handleNotAMondayError() {
        messageDialog.show(properties.getProperty(START_DATE_NOT_A_MONDAY_ERROR_MESSAGE));
    }

    /**
     * This function provides feedback to the user when the calendar
     * end date is not a Friday, which is forbidden.
     */
    public void handleNotAFridayError() {
        messageDialog.show(properties.getProperty(END_DATE_NOT_A_FRIDAY_ERROR_MESSAGE));
    }

    /**
     * This function provides feedback to the user when the calendar
     * start date that is selected is chronologically after the end
     * date that is selected, which is forbidden.
     */
    public void handleStartDateAfterEndDate() {
        messageDialog.show(properties.getProperty(START_DATE_AFTER_END_DATE_ERROR_MESSAGE));
    }

    /**
     * This function provides feedback to the user when the properties.xml
     * file cannot be loaded.
     */
    public void handlePropertiesFileError() {
        messageDialog.show(properties.getProperty(PROPERTIES_FILE_ERROR_MESSAGE));
    }
}
