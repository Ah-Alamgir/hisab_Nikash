package com.hanifsapp.hisabee.firebase_Db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Constant {
    public static DatabaseReference dbRef;
    public static DatabaseReference productList_ref;
    public static DatabaseReference SingleValue_ref;
    public static DatabaseReference todaySell;
    public static DatabaseReference todayDue;
    public static DatabaseReference todaySellHistory;
    public static DatabaseReference todayDueHistory;
    public static DatabaseReference todayCostHistory;


    public static void getDbRef(){
            dbRef= FirebaseDatabase.getInstance().getReference("denaPaona").child("userList").child("H00sdMoS4tPd3s4R48gxD82QXfr1");
            productList_ref = dbRef.child("ProductList");
            SingleValue_ref = dbRef.child("singleValues");
            todaySell = SingleValue_ref.child("todaySell");
            todayDue = SingleValue_ref.child("todayDue");


            todaySellHistory = dbRef.child("History").child("todaySellHistory");
            todayCostHistory = dbRef.child("History").child("todayCostHistory");
    }


}
