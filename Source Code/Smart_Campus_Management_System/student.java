package Smart_Campus_Management_System;

// Class for representing students
public class student {
    private String studentID;
    private String name;
    private String email;
    private String course;
    private int year;

    // Constructor
    public student(String studentID, String name, String course, int year, String email) {
        this.studentID = studentID;
        this.name = name;
        this.email = email;
        this.course = course;
        this.year = year;
    }
    
    // Getter methods
    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getYear() {
        return year;
    }

    public String getEmail() {
        return email;
    }
}
