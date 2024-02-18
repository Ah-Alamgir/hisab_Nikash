package com.hanifsapp.hisabee.firebase_Db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanifsapp.hisabee.utility.GetDate;


public class Constant {
    public static DatabaseReference dbRef;
    public static DatabaseReference productList_ref;
    public static DatabaseReference SingleValue_ref;

    public static DatabaseReference todayDue;
    public static DatabaseReference todayCost;
    public static DatabaseReference todaySellHistory;
    public static DatabaseReference todayDueHistory;
    public static DatabaseReference todayCostHistory;


    public static void getDbRef(){
        String dates = GetDate.getDate(0);
            dbRef= FirebaseDatabase.getInstance().getReference("denaPaona").child("userList").child("H00sdMoS4tPd3s4R48gxD82QXfr1");
            productList_ref = dbRef.child("ProductList");
            SingleValue_ref = dbRef.child("singleValues").child(dates);

            todayDue = SingleValue_ref.child("todayDue").child(dates);
            todayCost = SingleValue_ref.child("todayCost").child(dates);


            todaySellHistory = dbRef.child("History").child("todaySellHistory").child(dates);
            todayCostHistory = dbRef.child("History").child("todayCostHistory").child(dates);
    }


}
