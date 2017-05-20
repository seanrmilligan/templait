package csb.controller;

import static csb.CSB_PropertyType.REMOVE_ITEM_MESSAGE;
import csb.data.Course;
import csb.data.CourseDataManager;
import csb.data.Lecture;
import csb.gui.CSB_GUI;
import csb.gui.MessageDialog;
import csb.gui.LectureDialog;
import csb.gui.YesNoCancelDialog;
import java.util.List;
import javafx.stage.Stage;
import com.seanrmilligan.properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class LectureEditController {
	public static final int MOVE_UP = -1;
	public static final int MOVE_DOWN = 1;
	
    LectureDialog ld;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public LectureEditController(Stage initPrimaryStage, Course course, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        ld = new LectureDialog(initPrimaryStage, course, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddLectureRequest (CSB_GUI gui) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ld.showAddLectureDialog();
        
        // DID THE USER CONFIRM?
        if (ld.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Lecture l = ld.getLecture();
            
            // AND ADD IT AS A ROW TO THE TABLE
            course.addLecture(l);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleEditLectureRequest(CSB_GUI gui, Lecture itemToEdit) {
        CourseDataManager cdm = gui.getDataManager();
        Course course = cdm.getCourse();
        ld.showEditLectureDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (ld.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Lecture l = ld.getLecture();
            itemToEdit.setTopic(l.getTopic());
            itemToEdit.setSessions(l.getSessions());
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveLectureRequest(CSB_GUI gui, Lecture itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getCourse().removeLecture(itemToRemove);
        }
    }
	
	public int handleMoveLectureRequest(CSB_GUI gui, int indexOfSelected, int directionToMove) {
		List<Lecture> lectures = gui.getDataManager().getCourse().getLectures();
		int swapIndex = indexOfSelected + directionToMove;
		
		if(!(swapIndex >= lectures.size()) && !(swapIndex < 0)) {
			Lecture temp = lectures.get(indexOfSelected);
			
			lectures.set(indexOfSelected, lectures.get(swapIndex));
			lectures.set(swapIndex, temp);
			
			return swapIndex;
		}
		
		return indexOfSelected;
	}
}