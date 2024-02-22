package com.hanifsapp.hisabee.firebase_Db;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.model.ProductList;

import java.util.ArrayList;

public class GetproductList {


    public static ArrayList<ProductList> card_list= new ArrayList<ProductList>();
    public static ArrayList<String> added_tocard= new ArrayList<String>();
    public static MutableLiveData<ArrayList<ProductList>> product_list = new MutableLiveData<ArrayList<ProductList>>();


    public static int stocktotal = 0;
    public static int stockPrice = 0;


    public static void getProduct_item(){
        try {
                Constant.productList_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ProductList> productArray = new ArrayList<>();

                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            ProductList product = dataSnapshot.getValue(ProductList.class);
                            assert product != null;
                            product.setId(dataSnapshot.getKey());
                            stocktotal+= product.getStock();
                            stockPrice+= product.getSellPrice()*product.getStock();
                            productArray.add(product);
                        }
                        product_list.setValue(productArray);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }catch (DatabaseException e) {
        };

    }

}
