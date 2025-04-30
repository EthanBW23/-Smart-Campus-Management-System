package Smart_Campus_Management_System;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Manages a list of classroom objects: adding, removing, updating and saving to a file
public class classroomManager {
    private List<classroom> classrooms;
    private final String FILE_PATH = "classrooms.txt"; // File where classroom data is saved

    // Constructor
    public classroomManager() {
        classrooms = new ArrayList<>();
        loadFromFile();
    }

    // Adds a new classroom
    public void addClassroom(classroom Classroom) {
        classrooms.add(Classroom);
        saveToFile(); // Auto saves to file
    }

    // Removes a classroom by room number
    public void removeClassroom(String roomNumber) {
        classrooms.removeIf(Classroom -> Classroom.getRoomNumber().equals(roomNumber));
        saveToFile(); // Auto saves to file
    }

    // Updates an existing classroom entry
    public void updateClassroom(classroom updatedClassroom) {
        for (int i = 0; i < classrooms.size(); i++) {
            if (classrooms.get(i).getRoomNumber().equals(updatedClassroom.getRoomNumber())) {
                classrooms.set(i, updatedClassroom);
                break;
            }
        }
        saveToFile(); // Auto saves to file
    }

    // Returns the list of all classrooms
    public List<classroom> getAllClassrooms() {
        return classrooms;
    }

    // Saves classroom list to the file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (classroom Classroom : classrooms) {
                writer.write(Classroom.getRoomNumber() + "," + Classroom.getCapacity());
                writer.newLine();
            }
        } catch (IOException e) { // Fails to save (fails to write to the file)
            e.printStackTrace();
        }
    }

    // Loads classrooms from the file
    private void loadFromFile() {
        classrooms.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // No file exists yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String roomNumber = parts[0];
                    int capacity = Integer.parseInt(parts[1]);
                    classrooms.add(new classroom(roomNumber, capacity));
                }
            }
        } catch (IOException e) { // Fails to load (fails to read the file)
            e.printStackTrace();
        }
    }
}
