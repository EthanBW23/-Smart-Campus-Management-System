package Smart_Campus_Management_System;

// Class for representing classrooms
public class classroom {
    private String roomNumber;
    private int capacity;

    // Constructor
    public classroom(String roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    // Getter methods
    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }
}
