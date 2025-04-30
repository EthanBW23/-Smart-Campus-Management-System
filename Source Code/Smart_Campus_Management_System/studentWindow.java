package Smart_Campus_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// GUI window for managing Students (Add, Update, Delete, View)
public class studentWindow extends JPanel {
    private studentManager studentManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, courseField, yearField, emailField;

    // Constructor: sets up the window layout and components
    public studentWindow(studentManager manager) {
    	// Validates that there is always a valid studentManager to use
    	if (manager != null) {
    	    this.studentManager = manager;
    	} else {
    	    this.studentManager = new studentManager();
    	}

        setLayout(new BorderLayout());
        
        // Top input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 10, 5));
        idField = new JTextField();
        nameField = new JTextField();
        courseField = new JTextField();
        yearField = new JTextField();
        emailField = new JTextField();
        
        // Input panel labels
        inputPanel.add(new JLabel("Student ID", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Name", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Course", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Year", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Email", SwingConstants.CENTER));

        // Input text fields
        inputPanel.add(idField);
        inputPanel.add(nameField);
        inputPanel.add(courseField);
        inputPanel.add(yearField);
        inputPanel.add(emailField);

        add(inputPanel, BorderLayout.NORTH);

        // Centre table
        tableModel = new DefaultTableModel(new Object[]{"Student ID", "Name", "Course", "Year", "Email"}, 0);
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
        addButton.addActionListener(event -> addStudent());
        updateButton.addActionListener(event -> updateStudent());
        deleteButton.addActionListener(event -> deleteStudent());
    }

    // Add a new student
    private void addStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();
        String yearText = yearField.getText().trim();
        String email = emailField.getText().trim();

        // If input fields are empty
        if (id.isEmpty() || name.isEmpty() || course.isEmpty() || yearText.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly.");
            return;
        }
        
        // Checks if year isn't a integer (number)
        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a number.");
            return;
        }

        // Check for duplicate student ID
        for (student Student : studentManager.getAllStudents()) {
            if (Student.getStudentID().equals(id)) {
                JOptionPane.showMessageDialog(this, "Student ID already exists. Please use a different ID.");
                return;
            }
        }

        // Add student
        student newStudent = new student(id, name, course, year, email);
        studentManager.addStudent(newStudent);
        updateTable(); // Auto loads table data when a new classroom entry is added
        clearFields(); // Clears input fields automatically
        JOptionPane.showMessageDialog(this, "Student added successfully.");
    }

    // Update an existing student entry
    private void updateStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();
        String yearText = yearField.getText().trim();
        String email = emailField.getText().trim();

        // If input fields are empty
        if (id.isEmpty() || name.isEmpty() || course.isEmpty() || yearText.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly.");
            return;
        }

        // Checks if year isn't a integer (number)
        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a number.");
            return;
        }

        // Check if student exists
        boolean found = false;
        for (student Student : studentManager.getAllStudents()) {
            if (Student.getStudentID().equals(id)) {
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Student ID not found. Cannot update.");
            return;
        }

        // Update student
        student updatedStudent = new student(id, name, course, year, email);
        studentManager.updateStudent(updatedStudent);
        updateTable(); // Auto loads table data when a new classroom entry is added
        clearFields(); // Clears input fields automatically
        JOptionPane.showMessageDialog(this, "Student updated successfully.");
    }

    // Delete a student by ID
    private void deleteStudent() {
        String id = idField.getText().trim();

        // If student ID field is empty
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Student ID to delete.");
            return;
        }

        // Check if student exists
        boolean found = false;
        for (student Student : studentManager.getAllStudents()) {
            if (Student.getStudentID().equals(id)) {
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Student ID not found. Cannot delete.");
            return;
        }

        // Asks user for confirmation before deleting
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            studentManager.removeStudent(id);
            updateTable(); // Auto loads table data when a new classroom entry is added
            clearFields(); // Clears input fields automatically
            JOptionPane.showMessageDialog(this, "Student deleted successfully.");
        }
    }

    // Update the table with the latest data
    private void updateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (student Student : studentManager.getAllStudents()) {
            tableModel.addRow(new Object[]{Student.getStudentID(), Student.getStudentName(), Student.getCourse(), Student.getYear(), Student.getEmail()});
        }
    }

    // Clear input fields
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        yearField.setText("");
        emailField.setText("");
    }
}
