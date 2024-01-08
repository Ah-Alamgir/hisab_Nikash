package com.hanifsapp.hisabee.firebase_Db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class variable {
    public static DatabaseReference dbRef;
    public static DatabaseReference productList_ref;


    public static void getDbRef(String auth_User_id){
            dbRef= FirebaseDatabase.getInstance().getReference("denaPaona").child("userList").child(auth_User_id);
            productList_ref = dbRef.child("ProductList");
    }


}
