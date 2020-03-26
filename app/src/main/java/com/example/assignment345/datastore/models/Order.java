package com.example.assignment345.datastore.models;

import androidx.annotation.NonNull;

public class Order {

    public Order(int id, Double totalPrice, int meal_id, int quantity, Double tip) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.meal_id = meal_id;
        this.quantity = quantity;
        this.tip = tip;
    }

    private int id;
    private Double totalPrice;
    private int meal_id;

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    private String meal_name;
    private int quantity;
    private Double tip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ id: " + id + " name " + meal_name + "]";
    }
}
