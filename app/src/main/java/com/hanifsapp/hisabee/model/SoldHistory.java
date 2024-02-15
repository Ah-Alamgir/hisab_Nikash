package com.hanifsapp.hisabee.model;

public class SoldHistory {
    String description;
    String date;
    int price;
    int type;


    public SoldHistory(String description, int price, int type, String date) {
        this.description = description;
        this.price = price;
        this.type = type;
        this.date = date;
    }

    public String getDate() {
        return date;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
