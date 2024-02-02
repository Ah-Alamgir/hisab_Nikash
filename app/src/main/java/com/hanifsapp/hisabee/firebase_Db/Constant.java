package com.hanifsapp.hisabee.firebase_Db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Constant {
    public static DatabaseReference dbRef;
    public static DatabaseReference productList_ref;


    public static void getDbRef(){
            dbRef= FirebaseDatabase.getInstance().getReference("denaPaona").child("userList").child("H00sdMoS4tPd3s4R48gxD82QXfr1");
            productList_ref = dbRef.child("ProductList");
    }


}
