package com.example.assignment345.datastore.models;

import androidx.annotation.NonNull;

//id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price DECIMAL
public class Meal {

    public Meal(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private int id;

    private String name;

    private Double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
