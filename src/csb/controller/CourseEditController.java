package csb.controller;

import csb.data.Course;
import csb.error.ErrorHandler;
import csb.gui.CSB_GUI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

/**
 * This controller class handles the responses to all course
 * editing input, including verification of data and binding of
 * entered data to the Course object.
 * 
 * @author Richard McKenna
 */
public class CourseEditController {
    // WE USE THIS TO MAKE SURE OUR PROGRAMMED UPDATES OF UI
    // VALUES DON'T THEMSELVES TRIGGER EVENTS
    private boolean enabled;

    /**
     * Constructor that gets this controller ready, not much to
     * initialize as the methods for this function are sent all
     * the objects they need as arguments.
     */
    public CourseEditController() {
        enabled = true;
    }

    /**
     * This mutator method lets us enable or disable this controller.
     * 
     * @param enableSetting If false, this controller will not respond to
     * Course editing. If true, it will.
     */
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }

    /**
     * This controller function is called in response to the user changing
     * course details in the UI. It responds by updating the bound Course
     * object using all the UI values, including the verification of that
     * data.
     * 
     * @param gui The user interface that requested the change.
     */
    public void handleCourseChangeRequest(CSB_GUI gui) {
        if (enabled) {
            try {
                // UPDATE THE COURSE, VERIFYING INPUT VALUES
                gui.updateCourseInfo(gui.getDataManager().getCourse());
                
                // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
                // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
                // THE SAVE BUTTON IS ENABLED
                gui.getFileController().markAsEdited(gui);
            } catch (Exception e) {
                // SOMETHING WENT WRONG
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleUpdateCourseError();
            }
        }
    }

    /**
     * This controller function is called in response to the user changing
     * the start or end date for the course. It responds by verifying the 
     * change and then updating the bound Course object.
     * 
     * @param gui The user interface that has the date controls.
     * @param mondayPicker The date control for selecting the first
     * Monday of the semester.
     * @param fridayPicker The date control for selecting the last
     * Friday of the semester.
     */
    public void handleDateSelectionRequest(CSB_GUI gui, DatePicker mondayPicker, DatePicker fridayPicker) {
        if (enabled) {
            // NOTE THAT WE WILL IGNORE THIS EVENT WHEN IT IS DURING INITIALIZATION
            if (fridayPicker.getValue() == null) {
                return;
            }

            // GET THE DATA THE DatePicker CONTROLS CURRENTLY HOLD
            LocalDate monday = mondayPicker.getValue();
            LocalDate friday = fridayPicker.getValue();
            Course course = gui.getDataManager().getCourse();

            // IS MONDAY REALLY A MONDAY?
            if (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
                // TURN THE DAY FOR THIS DATE PICKER BACK TO WHAT THE COURSE HAS
                mondayPicker.setValue(course.getStartingMonday());

                // AND NOTIFY THE USER OF THE ERROR
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleNotAMondayError();
            } // IS FRIDAY REALLY A FRIDAY?
            else if (friday.getDayOfWeek() != DayOfWeek.FRIDAY) {
                // TURN THE DAY FOR THIS DATE PICKER BACK TO WHAT THE COURSE HAS
                fridayPicker.setValue(course.getEndingFriday());

                // AND NOTIFY THE USER OF THE ERROR
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleNotAFridayError();
            } // IS THE START DATE BEFORE THE END DATE?
            else if (monday.isAfter(friday)) {
            // TURN THEM BOTH TO WHAT COURSE HAS, THIS IS A LITTLE TRICKY
                // BECAUSE WE DON'T WANT TO GET IN AN ENDLESS CYCLE HERE

                // HERE WE ONLY HAVE TO MOVE FRIDAY BACK
                if (friday.isBefore(course.getStartingMonday())) {
                    fridayPicker.setValue(course.getEndingFriday());
                } // HERE WE ONLY HAVE TO MOVE MONDAY BACK
                else {
                    mondayPicker.setValue(course.getStartingMonday());
                }

                // AND NOTIFY THE USER OF THE ERROR
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleStartDateAfterEndDate();
            } // IN THIS CASE ALL IS GOOD
            else {
                // MAKE SURE THE COURSE HAS THE CHANGES
                gui.updateCourseInfo(gui.getDataManager().getCourse());
            }
        }
    }
}