package com.example.dogapi;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dogs")
public class Dog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String imageUrl;

    public Dog(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
