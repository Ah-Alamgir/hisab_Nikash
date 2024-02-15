package com.hanifsapp.hisabee;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;
import java.util.Map;

public class Autoload {
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    public static void deleteData(String id){
        Constant.productList_ref.child(id).removeValue();
    }

    public static void deleteFragmentData(String id, String tag){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("singleValues").child(tag);
        usersRef.child(id).removeValue();
    }


    public static ArrayList<Map<String, Object>> CustomerInfo = new ArrayList<>();
    public static ArrayList<Map<String, Object>> cardItem = new ArrayList<>();










    public static void getDataToUpdate(String tag, int userInputtedCost, String userInputDetails){
        DatabaseReference costRef = FirebaseDatabase.getInstance().getReference().child("denaPaona").child("singleValues").child(tag);
        String dates = GetDate.getDate();

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





    public static boolean getStockToUpdate(){
        DatabaseReference usersRef = rootRef.child("denaPaona").child("ProductList");
        for (Map<String, Object> cardItems : cardItem){
            int updatedStock;
            updatedStock  = Integer.parseInt(String.valueOf(cardItems.get("Stock"))) - Integer.parseInt(String.valueOf(cardItems.get("Order")));
            usersRef.child(String.valueOf(cardItems.get("id"))).child("Stock").setValue(updatedStock);
        }

        return true;
    }

}
