package com.movr.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private long price; // DIUBAH
    private int stock;

    public Product(int id, String name, String description, long price, int stock) { // DIUBAH
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public long getPrice() { return price; } // DIUBAH
    public void setPrice(long price) { this.price = price; } // DIUBAH
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}