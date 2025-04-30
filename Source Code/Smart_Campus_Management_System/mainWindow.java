package Smart_Campus_Management_System;

import javax.swing.*;
import java.awt.*;

// Main application GUI window for the Smart Campus Management System
public class mainWindow extends JFrame {

    // Manager for handling student data
    private studentManager sm = new studentManager();
    
    // Panel that dynamically changes based on which button is pressed
    private JPanel dynamicPanel;

    // Constructor: sets up the main window layout and components
    public mainWindow() {
        setTitle("Smart Campus Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the app on exit
        setSize(800, 600);
        setLocationRelativeTo(null); // Centre window on screen

        // Top button bar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centred, with gaps
        buttonPanel.setBackground(new Color(200, 200, 200)); // Light gray background

        // Buttons for switching sections
        JButton studentButton = new JButton("Students");
        JButton classroomButton = new JButton("Classrooms");
        JButton resourceButton = new JButton("Resources");

        buttonPanel.add(studentButton);
        buttonPanel.add(classroomButton);
        buttonPanel.add(resourceButton);

        // Dynamic panel (area where different windows are shown)
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new BorderLayout());
        dynamicPanel.setBackground(Color.WHITE);

        // Set up frame layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH); // Top bar
        getContentPane().add(dynamicPanel, BorderLayout.CENTER); // Centre area

        // Button actions (switch panels when pressed)
        studentButton.addActionListener(event -> switchPanel(new studentWindow(sm))); // Students section
        classroomButton.addActionListener(event -> switchPanel(new classroomWindow())); // Classrooms section
        resourceButton.addActionListener(event -> switchPanel(new resourceWindow())); // Resources section

        setVisible(true); // Make the window visible
    }

    // Method to switch the dynamic panel content
    private void switchPanel(JPanel newPanel) {
        dynamicPanel.removeAll(); // Remove old panel
        dynamicPanel.add(newPanel, BorderLayout.CENTER); // Add new panel
        dynamicPanel.revalidate(); // Refresh layout
        dynamicPanel.repaint(); // Redraw screen
    }
}
