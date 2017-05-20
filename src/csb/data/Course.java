package csb.data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a course to be edited and then used to generate a site.
 *
 * @author Richard McKenna
 */
public class Course {

    // THESE COURSE DETAILS DESCRIBE WHAT'S REQUIRED BY
    // THE COURSE SITE PAGES

    Subject subject;
    int number;
    String title;
    Semester semester;
    int year;
    Instructor instructor;
    LocalDate startingMonday;
    LocalDate endingFriday;
    List<CoursePage> pages;
    List<DayOfWeek> lectureDays;

    // THESE ARE THE THINGS WE'LL PUT IN OUR SCHEDULE PAGE
    ObservableList<ScheduleItem> scheduleItems;
    ObservableList<Lecture> lectures;
    ObservableList<Assignment> assignments;

    /**
     * Constructor for setting up a Course, it initializes the Instructor, which
     * would have already been loaded from a file.
     *
     * @param initInstructor The instructor for this course. Note that this can
     * be changed by getting the Instructor and then calling mutator methods on
     * it.
     */
    public Course(Instructor initInstructor) {
        // INITIALIZE THIS OBJECT'S DATA STRUCTURES
        pages = new ArrayList();
        lectureDays = new ArrayList();

        // AND KEEP THE INSTRUCTOR
        instructor = initInstructor;

        // INIT THE SCHEDULE STUFF
        scheduleItems = FXCollections.observableArrayList();
        lectures = FXCollections.observableArrayList();
        assignments = FXCollections.observableArrayList();
    }

    // BELOW ARE ALL THE ACCESSOR METHODS FOR A COURSE
    // AND THE MUTATOR METHODS. NOTE THAT WE'LL NEED TO CALL
    // THESE AS USERS INPUT VALUES IN THE GUI
    public boolean hasCoursePage(CoursePage testPage) {
        return pages.contains(testPage);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public LocalDate getStartingMonday() {
        return startingMonday;
    }

    public void setStartingMonday(LocalDate startingMonday) {
        this.startingMonday = startingMonday;
    }

    public LocalDate getEndingFriday() {
        return endingFriday;
    }

    public void setEndingFriday(LocalDate endingFriday) {
        this.endingFriday = endingFriday;
    }

    public void setScheduleDates(LocalDate initStartingMonday, LocalDate initEndingFriday) {
        setStartingMonday(initStartingMonday);
        setEndingFriday(initEndingFriday);
    }

    public void addPage(CoursePage pageToAdd) {
        pages.add(pageToAdd);
    }

    public List<CoursePage> getPages() {
        return pages;
    }

    public void selectPage(CoursePage coursePage) {
        if (!pages.contains(coursePage)) {
            pages.add(coursePage);
        }
    }

    public void unselectPage(CoursePage coursePage) {
        if (pages.contains(coursePage)) {
            pages.remove(coursePage);
        }
    }

    public List<DayOfWeek> getLectureDays() {
        return lectureDays;
    }

    // BELOW ARE ADDITIONAL METHODS FOR UPDATING A COURSE
    public void selectLectureDay(DayOfWeek dayOfWeek) {
        if (!lectureDays.contains(dayOfWeek)) {
            lectureDays.add(dayOfWeek);
        } else {
            lectureDays.remove(dayOfWeek);
        }
    }

    public void selectLectureDay(DayOfWeek dayOfWeek, boolean isSelected) {
        if (isSelected) {
            if (!lectureDays.contains(dayOfWeek)) {
                lectureDays.add(dayOfWeek);
            }
        } else {
            lectureDays.remove(dayOfWeek);
        }
    }

    public void clearPages() {
        pages.clear();
    }

    public void clearLectureDays() {
        lectureDays.clear();
    }

    public void clearScheduleItems() {
        scheduleItems.clear();
    }

    public void clearLectures() {
        lectures.clear();
    }

    public void clearHWs() {
        assignments.clear();
    }

    public void addLectureDay(DayOfWeek dayOfWeek) {
        lectureDays.add(dayOfWeek);
    }

    public boolean hasLectureDay(DayOfWeek dayOfWeek) {
        return lectureDays.contains(dayOfWeek);
    }

    public void addScheduleItem(ScheduleItem si) {
        scheduleItems.add(si);
        Collections.sort(scheduleItems);
    }

    public ObservableList<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public void removeScheduleItem(ScheduleItem itemToRemove) {
        scheduleItems.remove(itemToRemove);
    }

    public void addLecture(Lecture l) {
        lectures.add(l);
    }

    public ObservableList<Lecture> getLectures() {
        return lectures;
    }

    public void removeLecture(Lecture lectureToRemove) {
        lectures.remove(lectureToRemove);
    }

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public ObservableList<Assignment> getAssignments() {
        return assignments;
    }

    public void removeAssignment(Assignment assignmentToRemove) {
        assignments.remove(assignmentToRemove);
    }

    public HashMap<LocalDate, ScheduleItem> getScheduleItemMappings() {
        // GET THE SCHEDULE ITEM DATES FOR QUICK LOOKUP
        HashMap<LocalDate, ScheduleItem> holidayDates = new HashMap<>();
		
        for (ScheduleItem scheduleItem : scheduleItems) {
            holidayDates.put(scheduleItem.getDate(), scheduleItem);
        }
		
        return holidayDates;
    }
	
	public HashMap<LocalDate, Assignment> getAssignmentMappings() {
		HashMap<LocalDate, Assignment> dueDates = new HashMap<>();
		
		for (Assignment assignment : assignments) {
			dueDates.put(assignment.getDate(), assignment);
		}
		
		return dueDates;
	}
}
