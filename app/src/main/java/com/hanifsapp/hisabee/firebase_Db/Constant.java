package com.hanifsapp.hisabee.firebase_Db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanifsapp.hisabee.utility.GetDate;


public class Constant {
    public static DatabaseReference dbRef;
    public static DatabaseReference productList_ref;
    public static DatabaseReference SingleValue_ref;


    public static DatabaseReference todaySellHistory;
    public static DatabaseReference CostHistory;


    public static DatabaseReference thisMonthCost;
    public static DatabaseReference thisMonthSell;


    public static void getDbRef() {
        String dates = GetDate.getDate(0);
        GetDate.getMonth();
        GetDate.getTarikh();
        dbRef = FirebaseDatabase.getInstance().getReference("denaPaona").child("userList").child("H00sdMoS4tPd3s4R48gxD82QXfr1");
        productList_ref = dbRef.child("ProductList");
        SingleValue_ref = dbRef.child("singleValues").child(dates);

        CostHistory = dbRef.child("History").child("todayCostHistory");
        //CostHistory do not need any date cause date is going to be changed for next and previous buttons

        todaySellHistory = dbRef.child("History").child("todaySellHistory").child(dates);


        thisMonthCost = dbRef.child("MonthHistory").child("MonthCost").child(GetDate.Month).child(GetDate.Tarikh);
        thisMonthSell = dbRef.child("MonthHistory").child("MonthSell").child(GetDate.Month).child(GetDate.Tarikh);
    }


}
