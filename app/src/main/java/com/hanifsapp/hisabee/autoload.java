package com.hanifsapp.hisabee;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static void deleteFragmentData(String id, String tag){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("singleValues").child(tag);
        usersRef.child(id).removeValue();
    }


    public static void saveSingleData(String tag, String date, int price){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("singleValues").child(tag);
        usersRef.child(date).setValue(price);
    }


    static ArrayList<Map<String, Object>> productLists = new ArrayList<>();
    public static ArrayList<Map<String, Object>> todaydue = new ArrayList<>();
    public static ArrayList<Map<String, Object>> todayspend = new ArrayList<>();
    public static ArrayList<Map<String, Object>> todaysell = new ArrayList<>();
    static ArrayList<Map<String, Object>> costCalculations = new ArrayList<>();
    static ArrayList<Map<String, Object>> cardItem = new ArrayList<>();
    public static List<String> cardItem_list = new ArrayList<String>();


    static String todaysellamount = "000";
    static String todaydueamount= "000";
    static String todaycostamount = "000";

    public static void getData(){
        // Create a DatabaseReference object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("denaPaona");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productLists.clear();
                costCalculations.clear();
                cardItem.clear();
                todaydue.clear();
                todaysell.clear();
                todayspend.clear();
                cardItem_list.clear();


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
                            for (DataSnapshot keys : childSnapshot.getChildren()) {
                                String key = keys.getKey();
                                for (DataSnapshot costsnapshot : keys.getChildren()) {
                                    Map<String, Object> data = (Map<String, Object>) costsnapshot.getValue();
                                    data.put("date", costsnapshot.getKey());
                                    if (costsnapshot.getKey().toString().contains(dates)) {
                                        switch (key) {
                                            case "todayDue":
                                                todaydueamount = String.valueOf(data.get("price"));
                                                todaydue.add(data);
                                                break;
                                            case "todaySell":
                                                todaysellamount = String.valueOf(data.get("price"));
                                                todaysell.add(data);
                                                break;
                                            case "todaySpend":
                                                todaycostamount = String.valueOf(data.get("price"));
                                                todayspend.add(data);
                                                break;
                                        }
                                    }
                                }
                            }
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

        costRef.get().addOnCompleteListener(task -> {
            if (task.getResult().hasChild(dates)) {
                try {
                    int currentValue = task.getResult().child(dates).child("price").getValue(Integer.class);
                    String details = task.getResult().child(dates).child("details").getValue(String.class);
                    int updatedValue = currentValue + userInputtedCost;

                    details = details+ "\n" +userInputtedCost+ " টাকাঃ"+"\n"+ userInputDetails+"\n";
                    costRef.child(dates).child("price").setValue(updatedValue);
                    costRef.child(dates).child("details").setValue(details);
                }catch (Exception e) {}

            }else {
                costRef.child(dates).child("price").setValue(userInputtedCost);
                costRef.child(dates).child("details").setValue(userInputtedCost+ " টাকাঃ  "+"\n"+ userInputDetails+"\n");
            }
        });
    }







    public static boolean getStockToUpdat(){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("ProductList");
        for (Map<String, Object> cardItems : cardItem){
            int updatedStock;
            updatedStock  = Integer.valueOf(cardItems.get("Stock").toString()) - Integer.valueOf(cardItems.get("Order").toString());
            usersRef.child(cardItems.get("id").toString()).child("Stock").setValue(updatedStock);
        }

        return true;

    }

}
