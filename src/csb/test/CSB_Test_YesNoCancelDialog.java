package csb.test;

import static csb.CSB_StartupConstants.CLOSE_BUTTON_LABEL;
import csb.gui.MessageDialog;
import csb.gui.YesNoCancelDialog;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * This class is used to test our YesNoCancelDialog class. Using a separate
 * class like this lets us setup simple tests without all the other
 * stuff in the way.
 *
 * @author Richard McKenna
 */
public class CSB_Test_YesNoCancelDialog extends Application {
    public void start(Stage primaryStage) {
        // AND THIS WILL PROVIDE FEEDBACK FOR OUR TESTS
        MessageDialog messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);

        // THIS IS WHAT WE'RE TESTING
        YesNoCancelDialog yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
        
        // PRESS YES TEST
        yesNoCancelDialog.show("Press a Button");
        String selection = yesNoCancelDialog.getSelection();
        messageDialog.show("You pressed " + selection);   
        
        // PRESS NO TEST
        yesNoCancelDialog.show("Press NO");
        selection = yesNoCancelDialog.getSelection();
        messageDialog.show("You pressed " + selection);      
        
        // PRESS CANCEL TEST
        yesNoCancelDialog.show("Press CANCEL");
        selection = yesNoCancelDialog.getSelection();
        messageDialog.show("You pressed " + selection);
    }    

    /**
     * Starts our test app.
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }    
}