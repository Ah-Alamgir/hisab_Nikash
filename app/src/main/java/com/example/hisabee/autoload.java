package com.example.hisabee;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class autoload {
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    public static String dates;

    public static void deleteData(String id){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("ProductList");
        usersRef.child(id).removeValue();
    }


    public static void saveSingleData(String tag, String date, int price){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("singleValues").child(tag);
        usersRef.child(date).setValue(price);
    }
    static ArrayList<Map<String, Object>> productLists = new ArrayList<>();
    static ArrayList<Map<String, Object>> costCalculations = new ArrayList<>();
    static ArrayList<Map<String, Object>> cardItem = new ArrayList<>();
    public static List<String> cardItem_list = new ArrayList<String>();
    static Map<String, Object> singleValues = new HashMap<>();

    public static void getData(){
        // Create a DatabaseReference object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("denaPaona");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                productLists.clear();
                costCalculations.clear();
                cardItem.clear();
                cardItem_list.clear();
                singleValues.clear();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String category = childSnapshot.getKey();
                    switch (category) {
                        case "ProductList":
                            for (DataSnapshot productSnapshot : childSnapshot.getChildren()) {
                                Map<String, Object> product = (Map<String, Object>) productSnapshot.getValue();
                                product.put("id",productSnapshot.getKey());
                                productLists.add(product);
                            }
                            break;


                        case "costCalculation":
                            for (DataSnapshot productSnapshot : childSnapshot.getChildren()) {
                                Map<String, Object> cost = (Map<String, Object>) productSnapshot.getValue();
                                cost.put("id",productSnapshot.getKey());
                                costCalculations.add(cost);
                            }
                            break;

                        case "singleValues":
                            singleValues = (Map<String, Object>) childSnapshot.getValue();
                            homePage.setText();
                            break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public static void getCurrentMonthName(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        dates= dateFormat.format(calendar.getTime());
    }

    public static void getDataToUpdate(String tag, int userInputtedCost, String userInputDetails){
        DatabaseReference costRef = FirebaseDatabase.getInstance().getReference().child("denaPaona").child("singleValues").child(tag);

        costRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(dates)) {
                    try {
                        int currentValue = dataSnapshot.child(dates).child("price").getValue(Integer.class);
                        String details = dataSnapshot.child(dates).child("details").getValue(String.class);
                        int updatedValue = currentValue + userInputtedCost;

                        details = details+ "\n" +userInputtedCost+ "টাকা খরচের বিবরন"+"\n"+ userInputDetails+ "-------------------------"+"\n";
                        costRef.child(dates).child("price").setValue(updatedValue);
                        costRef.child(dates).child("details").setValue(details);
                    }catch (Exception e) {}

                }else {
                    costRef.child(dates).child("price").setValue(userInputtedCost);
                    costRef.child(dates).child("details").setValue(userInputDetails);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
