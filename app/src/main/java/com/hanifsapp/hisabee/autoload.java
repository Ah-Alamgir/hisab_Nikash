package com.hanifsapp.hisabee;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.activity.homePage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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


    public static ArrayList<Map<String, Object>> productLists = new ArrayList<>();
    public static ArrayList<Map<String, Object>> todaydue = new ArrayList<>();
    public static ArrayList<Map<String, Object>> CustomerInfo = new ArrayList<>();
    public static ArrayList<Map<String, Object>> todayspend = new ArrayList<>();
    public static ArrayList<Map<String, Object>> todaysell = new ArrayList<>();
    static ArrayList<Map<String, Object>> costCalculations = new ArrayList<>();
    public static ArrayList<Map<String, Object>> cardItem = new ArrayList<>();
    public static List<String> cardItem_list = new ArrayList<>();


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
                CustomerInfo.clear();


                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String category = childSnapshot.getKey();
                    switch (Objects.requireNonNull(category)) {
                        case "ProductList":
                            for (DataSnapshot productSnapshot : childSnapshot.getChildren()) {
                                Map<String, Object> product = (Map<String, Object>) productSnapshot.getValue();
                                product.put("id",productSnapshot.getKey());
                                productLists.add(product);
                            }
                            break;

                        case "singleValues":
                            for (DataSnapshot keys : childSnapshot.getChildren()) {
                                String key = keys.getKey();
                                switch (key) {
                                    case "CustomarList":
                                        for (DataSnapshot dataSnapshot1: keys.getChildren()) {
                                            CustomerInfo.add((Map<String, Object>) dataSnapshot1.getValue());
                                            Log.d("datamks", CustomerInfo.toString());
                                        }
                                        break;

                                    case "todayDue":
                                        for (DataSnapshot costsnapshot : keys.getChildren()) {
                                            Map<String, Object> dues = (Map<String, Object>) costsnapshot.getValue();
                                            dues.put("date", costsnapshot.getKey());
                                            if (Objects.requireNonNull(costsnapshot.getKey()).contains(dates)) {
                                                todaydueamount = String.valueOf(dues.get("price"));
                                            }
                                            todaydue.add(dues);
                                        }
                                        Collections.reverse(todaydue);
                                        break;
                                    case "todaySell":
                                        for (DataSnapshot costsnapshot : keys.getChildren()) {
                                            Map<String, Object> sells = (Map<String, Object>) costsnapshot.getValue();
                                            sells.put("date", costsnapshot.getKey());
                                            if (Objects.requireNonNull(costsnapshot.getKey()).contains(dates)) {
                                                todaysellamount = String.valueOf(sells.get("price"));
                                            }
                                            todaysell.add(sells);
                                        }
                                        Collections.reverse(todaysell);
                                        break;
                                    case "todaySpend":
                                        for (DataSnapshot costsnapshot : keys.getChildren()) {
                                            Map<String, Object> spends = (Map<String, Object>) costsnapshot.getValue();
                                            spends.put("date", costsnapshot.getKey());
                                            if (Objects.requireNonNull(costsnapshot.getKey()).contains(dates)) {
                                                todaycostamount = String.valueOf(spends.get("price"));
                                            }
                                            todayspend.add(spends);
                                        }
                                        Collections.reverse(todayspend);
                                        break;


                                }}

                            break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

                    details = details+ "\n" +userInputtedCost+ " টাকাঃ"+"\n"+ userInputDetails.replace("<b>","")+"\n\n";
                    costRef.child(dates).child("price").setValue(updatedValue);
                    costRef.child(dates).child("details").setValue(details);
                }catch (Exception ignored) {}

            }else {
                costRef.child(dates).child("price").setValue(userInputtedCost);
                costRef.child(dates).child("details").setValue(userInputtedCost+ " টাকাঃ  "+"\n"+ userInputDetails.replace("</b>", "")+"\n");
            }
        });
    }



    public static void isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isDataConnectionAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected() &&
                (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE ||
                        activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);

        if(!isDataConnectionAvailable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Network Unavailable")
                    .setMessage("Please check your internet connection.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }


    public static boolean getStockToUpdat(){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("ProductList");
        for (Map<String, Object> cardItems : cardItem){
            int updatedStock;
            updatedStock  = Integer.parseInt(String.valueOf(cardItems.get("Stock"))) - Integer.parseInt(String.valueOf(cardItems.get("Order")));
            usersRef.child(String.valueOf(cardItems.get("id"))).child("Stock").setValue(updatedStock);
        }

        return true;

    }

}
