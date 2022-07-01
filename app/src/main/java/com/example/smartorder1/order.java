package com.example.smartorder1;

public class order{
    private int id;
    private String name;
    private double price;
    private long quan;

    public order(int id, String name, double price, long quan) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quan = quan;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public long getQuan() {
        return quan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuan(long quan) {
        this.quan = quan;
    }
}

