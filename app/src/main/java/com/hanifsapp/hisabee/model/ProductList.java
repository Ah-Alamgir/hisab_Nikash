package com.hanifsapp.hisabee.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ProductList {
    String name,id;
    int Discount,Stock, buyPrice, sellPrice, vat;
    int order;

    public ProductList(){

    }

    public ProductList(int buyPrice, String name,int vat, int discount, int sellPrice, int stock) {
        this.name = name;
        Discount = discount;
        Stock = stock;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.vat = vat;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("name", name);
        result.put("discount", Discount);
        result.put("Stock", Stock);
        result.put("vat", vat);
        result.put("buyPrice", buyPrice);
        result.put("sellPrice", sellPrice);

        return result;
    }

    //for item to add to cartlist


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getDiscount() {
        return Discount;
    }

    public int getStock() {
        return Stock;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getVat() {
        return vat;
    }

    public void setId(String id) {
        this.id = id;
    }





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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
