package com.hanifsapp.hisabee.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class ProductList {
    String name, id;
    int Discount, Stock, buyPrice, sellPrice, vat;
    int order;

    public ProductList() {

    }

    public ProductList(int buyPrice, String name, int vat, int discount, int sellPrice, int stock) {
        this.name = name;
        Discount = discount;
        Stock = stock;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.vat = vat;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("name", name);
        result.put("discount", Discount);
        result.put("Stock", Stock);
        result.put("vat", vat);
        result.put("buyPrice", buyPrice);
        result.put("sellPrice", sellPrice);

        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    //for item to add to cartlist


    //for card items
    public ProductList(String name, String id, int discount, int stock, int buyPrice, int sellPrice, int vat, int order) {
        this.name = name;
        this.id = id;
        Discount = discount;
        Stock = stock;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.vat = vat;
        this.order = order;
    }

}

