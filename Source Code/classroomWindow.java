package Smart_Campus_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// GUI window for managing classrooms (Add, Update, Delete, View)
public class classroomWindow extends JPanel {
    private classroomManager classroomManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField roomNumberField, capacityField;

    // Constructor: sets up the window layout and components
    public classroomWindow() {
        this.classroomManager = new classroomManager();

        setLayout(new BorderLayout());

        // Top input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        roomNumberField = new JTextField();
        capacityField = new JTextField();

        // Input panel labels
        inputPanel.add(new JLabel("Room Number", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Capacity", SwingConstants.CENTER));

        // Input text fields
        inputPanel.add(roomNumberField);
        inputPanel.add(capacityField);

        add(inputPanel, BorderLayout.NORTH);

        // Centre table
        tableModel = new DefaultTableModel(new Object[]{"Room Number", "Capacity"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable direct/manual editing
            }
        };
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load existing data into table at start up
        updateTable();

        // Button event listeners
        addButton.addActionListener(event -> addClassroom());
        updateButton.addActionListener(event -> updateClassroom());
        deleteButton.addActionListener(event -> deleteClassroom());
    }

    // Add a new classroom
    private void addClassroom() {
        String roomNumber = roomNumberField.getText().trim();
        String capacityText = capacityField.getText().trim();
        
        // If input fields are empty
        if (roomNumber.isEmpty() || capacityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Checks if capacity isn't a integer (number)
        int capacity;
        try {
            capacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacity must be a number.");
            return;
        }

        // Check for duplicate room number
        for (classroom Classroom : classroomManager.getAllClassrooms()) {
            if (Classroom.getRoomNumber().equals(roomNumber)) {
                JOptionPane.showMessageDialog(this, "Room Number already exists. Please use a different number.");
                return;
            }
        }

        // Add classroom
        classroom newClassroom = new classroom(roomNumber, capacity);
        classroomManager.addClassroom(newClassroom);
        updateTable(); // Auto loads table data when a new classroom entry is added
        clearFields(); // Clears input fields automatically
        JOptionPane.showMessageDialog(this, "Classroom added successfully.");
    }

    // Update an existing classroom entry
    private void updateClassroom() {
        String roomNumber = roomNumberField.getText().trim();
        String capacityText = capacityField.getText().trim();

        // If input fields are empty
        if (roomNumber.isEmpty() || capacityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Checks if capacity isn't a integer (number)
        int capacity;
        try {
            capacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException e) { // Capacity isn't a integer (number)
            JOptionPane.showMessageDialog(this, "Capacity must be a number.");
            return;
        }

        // Checks if classroom exists
        boolean found = false;
        for (classroom Classroom : classroomManager.getAllClassrooms()) {
            if (Classroom.getRoomNumber().equals(roomNumber)) {
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Room Number not found. Cannot update.");
            return;
        }

        // Update classroom
        classroom updatedClassroom = new classroom(roomNumber, capacity);
        classroomManager.updateClassroom(updatedClassroom);
        updateTable(); // Auto loads table data when a classroom entry is updated
        clearFields();
        JOptionPane.showMessageDialog(this, "Classroom updated successfully.");
    }

    // Delete a classroom by room number
    private void deleteClassroom() {
        String roomNumber = roomNumberField.getText().trim();

        // If room number field is empty
        if (roomNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Room Number to delete.");
            return;
        }

        // Checks if classroom exists
        boolean found = false;
        for (classroom Classroom : classroomManager.getAllClassrooms()) {
            if (Classroom.getRoomNumber().equals(roomNumber)) {
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Room Number not found. Cannot delete.");
            return;
        }

        // Asks user for confirmation before deleting
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this classroom?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            classroomManager.removeClassroom(roomNumber);
            updateTable(); // Auto loads table data when a classroom entry is deleted
            clearFields();
            JOptionPane.showMessageDialog(this, "Classroom deleted successfully.");
        }
    }

    // Update the table with the latest data
    private void updateTable() {
        tableModel.setRowCount(0);
        for (classroom Classroom : classroomManager.getAllClassrooms()) {
            tableModel.addRow(new Object[]{Classroom.getRoomNumber(), Classroom.getCapacity()});
        }
    }

    // Clear input fields
    private void clearFields() {
        roomNumberField.setText("");
        capacityField.setText("");
    }
}
