package csb.controller;

import static csb.CSB_PropertyType.COURSE_SAVED_MESSAGE;
import static csb.CSB_PropertyType.NEW_COURSE_CREATED_MESSAGE;
import static csb.CSB_PropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static csb.CSB_StartupConstants.JSON_FILE_PATH_LAST_INSTRUCTOR;
import static csb.CSB_StartupConstants.PATH_COURSES;
import csb.data.Course;
import csb.data.CourseDataManager;
import csb.data.CoursePage;
import csb.data.Instructor;
import csb.error.ErrorHandler;
import csb.file.CourseFileManager;
import csb.file.CourseSiteExporter;
import csb.gui.CSB_GUI;
import csb.gui.MessageDialog;
import csb.gui.WebBrowser;
import csb.gui.YesNoCancelDialog;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import com.seanrmilligan.properties_manager.PropertiesManager;

/**
 * This controller class provides responses to interactions with the buttons in
 * the file toolbar.
 *
 * @author Richard McKenna
 */
public class FileController {

    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;

    // THIS GUY KNOWS HOW TO READ AND WRITE COURSE DATA
    private CourseFileManager courseIO;

    // THIS GUY KNOWS HOW TO EXPORT COURSE SCHEDULE PAGES
    private CourseSiteExporter exporter;

    // THIS WILL PROVIDE FEEDBACK TO THE USER WHEN SOMETHING GOES WRONG
    ErrorHandler errorHandler;
    
    // THIS WILL PROVIDE FEEDBACK TO THE USER AFTER
    // WORK BY THIS CLASS HAS COMPLETED
    MessageDialog messageDialog;
    
    // AND WE'LL USE THIS TO ASK YES/NO/CANCEL QUESTIONS
    YesNoCancelDialog yesNoCancelDialog;
    
    // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;

    /**
     * This default constructor starts the program without a course file being
     * edited.
     *
     * @param primaryStage The primary window for this application, which we
     * need to set as the owner for our dialogs.
     * @param initCourseIO The object that will be reading and writing course
     * data.
     * @param initExporter The object that will be exporting courses to Web
     * sites.
     */
    public FileController(
            MessageDialog initMessageDialog,
            YesNoCancelDialog initYesNoCancelDialog,
            CourseFileManager initCourseIO,
            CourseSiteExporter initExporter) {
        // NOTHING YET
        saved = true;
        
        // KEEP THESE GUYS FOR LATER
        courseIO = initCourseIO;
        exporter = initExporter;
        
        // BE READY FOR ERRORS
        errorHandler = ErrorHandler.getErrorHandler();
        
        // AND GET READY TO PROVIDE FEEDBACK
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        properties = PropertiesManager.getPropertiesManager();
    }
    
    /**
     * This method marks the appropriate variable such that we know
     * that the current Course has been edited since it's been saved.
     * The UI is then updated to reflect this.
     * 
     * @param gui The user interface editing the Course.
     */
    public void markAsEdited(CSB_GUI gui) {
        // THE Course OBJECT IS NOW DIRTY
        saved = false;
        
        // LET THE UI KNOW
        gui.updateToolbarControls(saved);
    }

    /**
     * This method starts the process of editing a new Course. If a course is
     * already being edited, it will prompt the user to save it first.
     * 
     * @param gui The user interface editing the Course.
     */
    public void handleNewCourseRequest(CSB_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                CourseDataManager dataManager = gui.getDataManager();
                dataManager.reset();
                saved = false;

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                gui.updateToolbarControls(saved);

                // TELL THE USER THE COURSE HAS BEEN CREATED
                messageDialog.show(properties.getProperty(NEW_COURSE_CREATED_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
            errorHandler.handleNewCourseError();
        }
    }

    /**
     * This method lets the user open a Course saved to a file. It will also
     * make sure data for the current Course is not lost.
     * 
     * @param gui The user interface editing the course.
     */
    public void handleLoadCourseRequest(CSB_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO OPEN A Course
            if (continueToOpen) {
                // GO AHEAD AND PROCEED LOADING A Course
                promptToOpen(gui);
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG
            errorHandler.handleLoadCourseError();
        }
    }

    /**
     * This method will save the current course to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     * 
     * @param gui The user interface editing the Course.
     * 
     * @param courseToSave The course being edited that is to be saved to a file.
     */
    public void handleSaveCourseRequest(CSB_GUI gui, Course courseToSave) {
        try {
            // SAVE IT TO A FILE
            courseIO.saveCourse(courseToSave);

            // MARK IT AS SAVED
            saved = true;

            // TELL THE USER THE FILE HAS BEEN SAVED
            messageDialog.show(properties.getProperty(COURSE_SAVED_MESSAGE));

            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            gui.updateToolbarControls(saved);
        } catch (IOException ioe) {
            errorHandler.handleSaveCourseError();
        }
    }

    /**
     * This method will export the current course.
     * 
     * @param gui
     */
    public void handleExportCourseRequest(CSB_GUI gui) {
        // EXPORT THE COURSE
        CourseDataManager dataManager = gui.getDataManager();
        Course courseToExport = dataManager.getCourse();		

        // WE'LL NEED THIS TO LOAD THE EXPORTED PAGE FOR VIEWING
        String courseURL = exporter.getPageURLPath(courseToExport, CoursePage.SCHEDULE);
        
        // NOW GET THE EXPORTER
        try {            
            
            // AND EXPORT THE COURSE
            exporter.exportCourseSite(courseToExport);
            
            // AND THEN OPEN UP THE PAGE IN A BROWSER
            Stage webBrowserStage = new Stage();
            WebBrowser webBrowser = new WebBrowser(webBrowserStage, courseURL);
            webBrowserStage.show();
        }
        // WE'LL HANDLE COURSE EXPORT PROBLEMS AND COURSE PAGE VIEWING
        // PROBLEMS USING DIFFERENT ERROR MESSAGES
        catch (MalformedURLException murle) {
            errorHandler.handleViewSchedulePageError(courseURL);
        } catch (Exception ioe) {
            errorHandler.handleExportCourseError(courseToExport);
        }
    }

    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     * 
     * @param gui
     */
    public void handleExitRequest(CSB_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handleExitError();
        }
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * Course, or opening another Course. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is returned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave(CSB_GUI gui) throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(properties.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            // SAVE THE COURSE
            CourseDataManager dataManager = gui.getDataManager();
            courseIO.saveCourse(dataManager.getCourse());
            saved = true;
            
            // AND THE INSTRUCTOR INFO
            Instructor lastInstructor = dataManager.getCourse().getInstructor();
            courseIO.saveLastInstructor(lastInstructor, JSON_FILE_PATH_LAST_INSTRUCTOR);
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(YesNoCancelDialog.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }
	
	public CourseSiteExporter getExporter() {
		return this.exporter;
	}

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen(CSB_GUI gui) {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser courseFileChooser = new FileChooser();
        courseFileChooser.setInitialDirectory(new File(PATH_COURSES));
        File selectedFile = courseFileChooser.showOpenDialog(gui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                Course courseToLoad = gui.getDataManager().getCourse();
                courseIO.loadCourse(courseToLoad, selectedFile.getAbsolutePath());
                gui.reloadCourse(courseToLoad);
                saved = true;
                gui.updateToolbarControls(saved);
                Instructor lastInstructor = courseToLoad.getInstructor();
                courseIO.saveLastInstructor(lastInstructor, JSON_FILE_PATH_LAST_INSTRUCTOR);
            } catch (Exception e) {
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleLoadCourseError();
            }
        }
    }

    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the course is changed in some way.
     */
    public void markFileAsNotSaved() {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current course has been saved
     * since it was last edited.
     *
     * @return true if the current course is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }
}
