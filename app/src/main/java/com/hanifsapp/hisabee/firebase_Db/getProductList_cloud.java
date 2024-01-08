package com.hanifsapp.hisabee.localDb;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.autoload;

import java.util.ArrayList;
import java.util.Map;

public class getProductList_cloud {
    public static ArrayList<Map<String, Object>> productLists = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static void getProductList(){
        try {
            if(autoload.dbRef==null){
                autoload.dbRef.child("productList").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("entry", String.valueOf(snapshot.getValue()));
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Map<String,Object> product = (Map<String, Object>) dataSnapshot.getValue();
                            product.put("id",dataSnapshot.getKey());
                            productLists.add(product);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }catch (DatabaseException e) {};

    }
}
