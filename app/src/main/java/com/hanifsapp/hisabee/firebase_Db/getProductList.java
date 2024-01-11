package com.hanifsapp.hisabee.firebase_Db;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.model.ProductList;

import java.util.ArrayList;

public class getProductList {
    public static ArrayList<ProductList> product_Lists = new ArrayList<ProductList>();
    public static ArrayList<ProductList> card_list= new ArrayList<ProductList>();
    public static ArrayList<String> added_tocard= new ArrayList<String>();


    public static void getProduct_item(){
        try {
                variable.productList_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            ProductList product = dataSnapshot.getValue(ProductList.class);
                            product.setId(dataSnapshot.getKey());
                            product_Lists.add(product);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        }catch (DatabaseException e) {
        };

    }




    public static boolean getStockToUpdate(){
        for (ProductList cardItems : card_list ){
            int updatedStock;
            updatedStock  = cardItems.getStock() - cardItems.getOrder();
            variable.dbRef.child(cardItems.getId()).child("Stock").setValue(updatedStock);
        }

        return true;
    }
}
