package com.hanifsapp.hisabee.model;

public class SoldHistory {
    String date;
    int price;


    public SoldHistory() {

    }

    public SoldHistory(String date, int price) {

        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
