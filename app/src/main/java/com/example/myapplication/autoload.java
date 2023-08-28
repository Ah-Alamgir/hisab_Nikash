package com.example.myapplication;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class autoload {
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();















    public static  List<Map<String, Object>> data = new ArrayList<>();


    public static void getProductData(){

        DatabaseReference usersRef = rootRef.child("ProductList");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = new HashMap<>();
                    for(DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), child.getValue());
                        map.put("id", ds.getKey());
                    }
                    data.add(map);
                }
                Log.d("datam", data.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);

    }



    public static void ddeleteData(String id){
        DatabaseReference usersRef = rootRef.child("ProductList");
        usersRef.child(id).removeValue();
    }

}
