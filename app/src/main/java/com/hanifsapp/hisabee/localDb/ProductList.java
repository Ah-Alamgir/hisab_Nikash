package com.hanifsapp.hisabee.localDb;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ProductList {
    String name,id;
    int Discount,Stock, buyPrice, sellPrice, vat;

    public ProductList(){

    }

    public ProductList(String name, int discount, int stock, int buyPrice, int sellPrice, int vat) {
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
        result.put("stock", Stock);
        result.put("vat", vat);
        result.put("buyPrice", buyPrice);
        return result;
    }


    @Exclude
    public ArrayList<Map<String, Object>> getItem_toSell() {
        ArrayList<Map<String, Object>> getItem = new ArrayList<Map<String, Object>>();


        return getItem;
    }









}
