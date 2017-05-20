package csb.file;

import csb.data.Course;
import csb.data.Instructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface provides an abstraction of what a file manager should do. Note
 * that file managers know how to read and write courses, instructors, and subjects,
 * but now how to export sites.
 * 
 * @author Richard McKenna
 */
public interface CourseFileManager {
    public void                 saveCourse(Course courseToSave) throws IOException;
    public void                 loadCourse(Course courseToLoad, String coursePath) throws IOException;
    public void                 saveLastInstructor(Instructor lastInstructor, String filePath) throws IOException;    
    public Instructor           loadLastInstructor(String filePath) throws IOException;
    public void                 saveSubjects(List<Object> subjects, String filePath) throws IOException;
    public ArrayList<String>    loadSubjects(String filePath) throws IOException;
}
