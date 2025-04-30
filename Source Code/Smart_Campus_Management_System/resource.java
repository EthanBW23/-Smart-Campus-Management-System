package Smart_Campus_Management_System;

//Class for representing resources
public class resource {
    private String resourceID;
    private String name;
    private String type;
    private int quantity;

    // Constructor
    public resource(String resourceID, String name, String type, int quantity) {
        this.resourceID = resourceID;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }
    
    // Getter methods
    public String getResourceID() {
        return resourceID;
    }

    public String getResourceName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}
