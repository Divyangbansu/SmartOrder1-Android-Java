package com.example.smartorder1;


public class cart{
    private int id;
    private String name;
    private double price;
    private long quan;
    private byte[] image;

    public cart(int id, String name, double price, long quan, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quan = quan;
        this.image = image;
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

    public byte[] getImage() {
        return image;
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

    public void setImage(byte[] image) {
        this.image = image;
    }
}
