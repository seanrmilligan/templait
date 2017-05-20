package csb.gui;

import java.net.MalformedURLException;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class provides a bare-bones simple little Web browser that
 * is both CSS and Java enabled so that we may verify the construction
 * of individual Web pages.
 * 
 * @author Richard McKenna
 */
public class WebBrowser {
    // THIS IS A SIMPLE WEB BROWSER WITHOUT NAVIGATION
    // CONTROLS, SO IT DOESN'T HAVE MUCH
    Stage browserStage;
    WebView browserView;
    WebEngine browserEngine;
    BorderPane browserPane;
    Scene browserScene;
    
    /**
     * This constructor loads the pageURLPath into the initBrowserStage. Note
     * that it actually loads it into another stage.
     * 
     * @param initBrowserStage Stage to display Web page.
     * @param pageURLPath URL of Web page to load and display.
     * @throws MalformedURLException This exception will be thrown if
     * a bad URL is provided.
     */
    public WebBrowser(Stage initBrowserStage, String pageURLPath) throws MalformedURLException {
        // THIS WILL SERVE AS OUR BROWSER WINDOW
        browserStage = initBrowserStage;
        
        // MAKE THE WEB VIEW AND LOAD THE WEB PAGE INTO IT
        browserView = new WebView();
        browserEngine = browserView.getEngine();
        browserEngine.load(pageURLPath);
        
        // PUT THE WEB VIEW IN THE WINDOW
        browserPane = new BorderPane();
        browserPane.setCenter(browserView);
        browserScene = new Scene(browserPane);
        browserStage.setScene(browserScene);
    }
}
