package your.package.name;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int id;
    private String name;
    private String course; // Starter, Main Course, Dessert
    private double price;

    public MenuItem(int id, String name, String course, double price) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
