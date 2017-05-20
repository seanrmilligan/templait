package csb.data;

/**
 * A simple data class for storing information about
 * an instructor.
 * 
 * @author Richard McKenna
 */
public class Instructor {
    String name;
    String homepageURL; 
    
    public Instructor(String initName, String initHomepageURL) {
        name = initName;
        homepageURL = initHomepageURL;
    }

    public String getName() {
        return name;
    }

    public String getHomepageURL() {
        return homepageURL;
    }

    public void setName(String initName) {
        name = initName;
    }

    public void setHomepageURL(String homepageURL) {
        this.homepageURL = homepageURL;
    }
}
