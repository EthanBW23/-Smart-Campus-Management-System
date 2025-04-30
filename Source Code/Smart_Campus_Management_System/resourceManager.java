package Smart_Campus_Management_System;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Manages a list of resource objects: adding, removing, updating, and saving to a file
public class resourceManager {
    private List<resource> resources;
    private final String FILE_PATH = "resources.txt"; // File where resource data is saved

    // Constructor
    public resourceManager() {
        resources = new ArrayList<>();
        loadFromFile();
    }

    // Adds a new resource
    public void addResource(resource Resource) {
        resources.add(Resource);
        saveToFile(); // Auto saves to file
    }

    // Removes a resource by resource ID
    public void removeResource(String resourceID) {
        resources.removeIf(Resource -> Resource.getResourceID().equals(resourceID));
        saveToFile(); // Auto saves to file
    }

    // Updates an existing resource entry
    public void updateResource(resource updatedResource) {
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getResourceID().equals(updatedResource.getResourceID())) {
                resources.set(i, updatedResource);
                break;
            }
        }
        saveToFile(); // Auto saves to file
    }

    // Returns the list of all resources
    public List<resource> getAllResources() {
        return resources;
    }

    // Saves resource list to the file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (resource Resource : resources) {
                writer.write(Resource.getResourceID() + "," +
                			Resource.getResourceName() + "," +
                			Resource.getType() + "," +
                			Resource.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) { // Fails to save (fails to write to the file)
            e.printStackTrace();
        }
    }

    // Loads resources from the file
    private void loadFromFile() {
        resources.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // No file exists yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String type = parts[2];
                    int quantity = Integer.parseInt(parts[3]);
                    resources.add(new resource(id, name, type, quantity));
                }
            }
        } catch (IOException e) { // Fails to load (fails to read the file)
            e.printStackTrace();
        }
    }
}
