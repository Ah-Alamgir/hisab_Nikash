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
import java.util.Objects;

public class autoload {
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();















    public static  List<Map<String, Object>> data = new ArrayList<>();
    public static  List<Map<String, Object>> cardIem = new ArrayList<>();

    public static List<String> cardItem_list = new ArrayList<String>();
    public static void getProductData(){

        DatabaseReference usersRef = rootRef.child("ProductList");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", ds.getKey());
                    for(DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), child.getValue());
                    }
                    data.add(map);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);

    }




    public static  List<Map<String, Object>> denapaonaGive = new ArrayList<>();
    public static  List<Map<String, Object>> denapaonatake = new ArrayList<>();
    public static  Map<String, Object> singleValuesMap  = new HashMap<>();
    public static void denapaonaHistory(String childName){
        DatabaseReference usersRef;
        if (Objects.equals(childName, "give") || Objects.equals(childName, "take")){
            usersRef = rootRef.child("denaPaona").child("history").child(childName);
        }else {
            usersRef = rootRef.child("denaPaona").child("singleValues");
        }
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", ds.getKey());
                    for(DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), child.getValue());
                    }


                    if(childName.equals("give")){
                        denapaonaGive.add(map);
                    }if (childName.equals("singleValues")){
                        singleValuesMap = map;
                    }else {
                        denapaonatake.add(map);
                    }



                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);

    }



    public static void deleteData(String id){
        DatabaseReference usersRef = rootRef.child("ProductList");
        usersRef.child(id).removeValue();
    }


    static ArrayList<Map<String, Object>> productLists = new ArrayList<>();
    static Map<String, Object> singleValues = new HashMap<>();
    static ArrayList<Map<String, Object>> give = new ArrayList<>();
    static ArrayList<Map<String, Object>> take = new ArrayList<>();
    public static void getData(){
        // Create a DatabaseReference object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("denaPaona");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String category = childSnapshot.getKey();
                    switch (category) {
                        case "ProductList":
                            for (DataSnapshot productSnapshot : childSnapshot.getChildren()) {
                                Map<String, Object> product = (Map<String, Object>) productSnapshot.getValue();
                                productLists.add(product);
                            }
                            break;
                        case "singleValues":
                            singleValues = (Map<String, Object>) childSnapshot.getValue();
                            break;
                        case "history":
                            for (DataSnapshot historySnapshot : childSnapshot.getChildren()) {
                                String historyType = historySnapshot.getKey();
                                switch (historyType) {
                                    case "give":
                                        for (DataSnapshot giveSnapshot : historySnapshot.getChildren()) {
                                            Map<String, Object> giveItem = (Map<String, Object>) giveSnapshot.getValue();
                                            give.add(giveItem);
                                        }
                                        break;
                                    case "take":
                                        for (DataSnapshot takeSnapshot : historySnapshot.getChildren()) {
                                            Map<String, Object> takeItem = (Map<String, Object>) takeSnapshot.getValue();
                                            take.add(takeItem);
                                        }
                                        break;
                                }
                            }
                            break;
                    }
                }
                Log.d("datam", productLists.toString());
                Log.d("datam", singleValues.toString());
                Log.d("datam", give.toString());
                Log.d("datam", take.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
