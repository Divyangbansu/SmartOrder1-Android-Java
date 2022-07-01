package com.example.smartorder1;

public class fooddisplay {
    private int id;
    private String name;
    private int price;
    private double gram;
    private byte[] image;

    public fooddisplay(int id, String name, int price, double gram, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.gram = gram;
        this.image = image;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getGram() {
        return gram;
    }

    public void setGram(double gram) {
        this.gram = gram;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

