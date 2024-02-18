package com.hanifsapp.hisabee.model;

public class CostHistory {
    int amount, type;
    String date;
    public CostHistory() {
    }

    public CostHistory(int amount, int type) {
        this.amount = amount;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
