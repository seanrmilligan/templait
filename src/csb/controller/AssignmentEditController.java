package csb.controller;

import static csb.CSB_PropertyType.REMOVE_ITEM_MESSAGE;
import csb.data.Assignment;
import csb.data.Course;
import csb.data.CourseDataManager;
import csb.gui.AssignmentDialog;
import csb.gui.CSB_GUI;
import csb.gui.MessageDialog;
import csb.gui.YesNoCancelDialog;
import java.util.Collections;
import java.util.Comparator;
import javafx.stage.Stage;
import com.seanrmilligan.properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class AssignmentEditController {	
    AssignmentDialog ad;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public AssignmentEditController(Stage initPrimaryStage, Course course, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        ad = new AssignmentDialog(initPrimaryStage, course, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddAssignmentRequest (CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ad.showAddAssignmentDialog();
        
        // DID THE USER CONFIRM?
        if (ad.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Assignment a = ad.getAssignment();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addAssignment(a);
			
			Collections.sort(course.getAssignments(), (Assignment a1, Assignment a2) -> a1.getDate().compareTo(a2.getDate()));
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleEditAssignmentRequest(CSB_GUI gui, Assignment itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ad.showEditAssignmentDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (ad.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Assignment a = ad.getAssignment();
			itemToEdit.setName(a.getName());
            itemToEdit.setTopics(a.getTopics());
			itemToEdit.setDate(a.getDate());
						
			Collections.sort(course.getAssignments(), (Assignment a1, Assignment a2) -> a1.getDate().compareTo(a2.getDate()));
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveAssignmentRequest(CSB_GUI gui, Assignment itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeAssignment(itemToRemove);
        }
    }
}