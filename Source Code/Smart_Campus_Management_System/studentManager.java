package Smart_Campus_Management_System;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Manages a list of student objects: adding, removing, updating, and saving to a file
public class studentManager {
    private List<student> students;
    private final String FILE_PATH = "students.txt"; // File where student data is saved

    // Constructor
    public studentManager() {
        students = new ArrayList<>();
        loadFromFile();
    }

    // Adds a new student
    public void addStudent(student Student) {
        students.add(Student);
        saveToFile(); // Auto saves to file
    }

    // Removes a student by student ID
    public void removeStudent(String studentID) {
        students.removeIf(Student -> Student.getStudentID().equals(studentID));
        saveToFile(); // Auto saves to file
    }

    // Updates an existing student entry
    public void updateStudent(student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID().equals(updatedStudent.getStudentID())) {
                students.set(i, updatedStudent);
                break;
            }
        }
        saveToFile(); // Auto saves to file
    }

    // Returns the full list of students
    public List<student> getAllStudents() {
        return students;
    }

    // Saves student list to the file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (student Student : students) {
                writer.write(Student.getStudentID() + "," +
                			Student.getStudentName() + "," +
                			Student.getCourse() + "," +
                			Student.getYear() + "," +
                			Student.getEmail());
                writer.newLine();
            }
        } catch (IOException e) { // Fails to save (fails to write to the file)
            e.printStackTrace();
        }
    }

    // Loads students from the file
    private void loadFromFile() {
        students.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // No file exists yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String name = parts[1];
                    String course = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    String email = parts[4];
                    students.add(new student(id, name, course, year, email));
                }
            }
        } catch (IOException e) { // Fails to load (fails to read the file)
            e.printStackTrace();
        }
    }
}
