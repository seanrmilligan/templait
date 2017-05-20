package csb.test;

import csb.data.Course;
import csb.data.CoursePage;
import csb.data.Instructor;
import csb.file.JsonCourseFileManager;
import csb.file.CourseSiteExporter;
import csb.gui.WebBrowser;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is used to test our site exporter as well as our Web Browser,
 * which is used to verify site exporting. Using a separate class like
 * this lets us setup simple tests without all the other stuff in the way.
 *
 * @author Richard McKenna
 */
public class CSB_Test_SiteExporterAndWebBrowser extends Application {
    // WE'LL DO SITE EXPORTING WITHOUT USING THE REGULAR GUI,
    // SO WE'LL HAVE TO LOAD A COURSE FROM A FILE OURSELVES
    static JsonCourseFileManager fileManager;
    static CourseSiteExporter exporter;
    static String sitesPath = "./sites/";
    static String basePath = sitesPath + "/base/";

    // THIS FILE HAD BETTER EXIST IF WE WANT THIS TEST TO SUCCEED
    static String jsonPath = "./data/courses/CSE393.json";

    // THIS IS OUR TEST COURSE
    static Course testCourse;

    /**
     * Here's where we'll do the test.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // WE'LL NEED AN INSTRUCTOR TO MAKE A COURSE
            Instructor testInstructor = new Instructor("Joe Shmo", "http://www.joeshmo.com");

            // INIT A NEW TEST COURSE
            testCourse = new Course(testInstructor);

            // INIT THE FILE MANAGER AND SITE EXPORTER
            fileManager = new JsonCourseFileManager();
            exporter = new CourseSiteExporter(basePath, sitesPath);

            // LOAD THE COURSE FROM THE JSON FILE
            fileManager.loadCourse(testCourse, jsonPath);
            
            // AND NOW EXPORT THE SITE
            exporter.exportCourseSite(testCourse);

            // NOW OPEN OUR EXPORTED COURSE IN OUR BROWSER
            String coursePagePath = exporter.getPageURLPath(testCourse, CoursePage.SCHEDULE);
            WebBrowser wB = new WebBrowser(primaryStage, coursePagePath);
            primaryStage.show();
        } catch (Exception e) {
            // SOMETHING WENT WRONG
            System.out.println("ERROR READING FROM " + jsonPath);
            e.printStackTrace();
        }
    }

    /**
     * All our main method does is start the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
