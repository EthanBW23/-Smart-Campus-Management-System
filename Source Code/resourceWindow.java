package Smart_Campus_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// GUI window for managing Resources (Add, Update, Delete, View)
public class resourceWindow extends JPanel {
    private resourceManager resourceManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, typeField, quantityField;

    // Constructor: sets up the window layout and components
    public resourceWindow() {
        this.resourceManager = new resourceManager();

        setLayout(new BorderLayout());

        // Top input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        idField = new JTextField();
        nameField = new JTextField();
        typeField = new JTextField();
        quantityField = new JTextField();

        // Input panel labels
        inputPanel.add(new JLabel("Resource ID", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Name", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Type", SwingConstants.CENTER));
        inputPanel.add(new JLabel("Quantity", SwingConstants.CENTER));

        // Input text fields
        inputPanel.add(idField);
        inputPanel.add(nameField);
        inputPanel.add(typeField);
        inputPanel.add(quantityField);

        add(inputPanel, BorderLayout.NORTH);

        // Centre Table
        tableModel = new DefaultTableModel(new Object[]{"Resource ID", "Name", "Type", "Quantity"}, 0);
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
        addButton.addActionListener(event -> addResource());
        updateButton.addActionListener(event -> updateResource());
        deleteButton.addActionListener(event -> deleteResource());
    }

    // Add a new resource
    private void addResource() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String quantityText = quantityField.getText().trim();

        // If input fields are empty
        if (id.isEmpty() || name.isEmpty() || type.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Checks if quantity isn't a integer (number)
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
            return;
        }

        // Check for duplicate resource ID
        for (resource Resource : resourceManager.getAllResources()) {
            if (Resource.getResourceID().equals(id)) {
                JOptionPane.showMessageDialog(this, "Resource ID already exists. Please use a different ID.");
                return;
            }
        }

        // Add resource
        resource newResource = new resource(id, name, type, quantity);
        resourceManager.addResource(newResource);
        updateTable(); // Auto loads table data when a new classroom entry is added
        clearFields(); // Clears input fields automatically
        JOptionPane.showMessageDialog(this, "Resource added successfully.");
    }

    // Update an existing resource entry
    private void updateResource() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String quantityText = quantityField.getText().trim();

        // If input fields are empty
        if (id.isEmpty() || name.isEmpty() || type.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Checks if quantity isn't a integer (number)
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
            return;
        }

        // Check if resource exists
        boolean found = false;
        for (resource Resource : resourceManager.getAllResources()) {
            if (Resource.getResourceID().equals(id)) {
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Resource ID not found. Cannot update.");
            return;
        }

        // Update resource
        resource updatedResource = new resource(id, name, type, quantity);
        resourceManager.updateResource(updatedResource);
        updateTable(); // Auto loads table data when a new classroom entry is added
        clearFields(); // Clears input fields automatically
        JOptionPane.showMessageDialog(this, "Resource updated successfully.");
    }

    // Delete a resource by ID
    private void deleteResource() {
        String id = idField.getText().trim();

        // If resource ID field is empty
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Resource ID to delete.");
            return;
        }

        // Check if resource exists
        boolean found = false;
        for (resource Resource : resourceManager.getAllResources()) {
            if (Resource.getResourceID().equals(id)) {
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Resource ID not found. Cannot delete.");
            return;
        }

        // Asks user for confirmation before deleting
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this resource?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            resourceManager.removeResource(id);
            updateTable(); // Auto loads table data when a new classroom entry is added
            clearFields(); // Clears input fields automatically
            JOptionPane.showMessageDialog(this, "Resource deleted successfully.");
        }
    }

    // Update the table with the latest data
    private void updateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (resource Resource : resourceManager.getAllResources()) {
            tableModel.addRow(new Object[]{Resource.getResourceID(), Resource.getResourceName(), Resource.getType(), Resource.getQuantity()});
        }
    }

    // Clear input fields
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        typeField.setText("");
        quantityField.setText("");
    }
}
