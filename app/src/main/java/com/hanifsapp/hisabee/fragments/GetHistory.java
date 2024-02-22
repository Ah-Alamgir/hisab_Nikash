package com.hanifsapp.hisabee.fragments;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.model.CostHistory;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;
import java.util.Optional;

public class GetHistory {

    public static MutableLiveData<ArrayList<Integer>> getGraph = new MutableLiveData<>();
    public static ArrayList<String> dates = new ArrayList<>();
    public static int totalSold = 0;

    public static void getSoldHistory() {

        ArrayList<Integer> amount = new ArrayList<>();
        Task task4 = Constant.todaySellHistory.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dates.add(dataSnapshot.getKey());
                    amount.add(dataSnapshot.getValue(Integer.class));
                    totalSold+= dataSnapshot.getValue(Integer.class);
                }
            }
        });


        Tasks.whenAllComplete(task4).addOnCompleteListener(task -> {
            getGraph.setValue(amount);
            getTodayTotalSell();
        });

    }


    public static MutableLiveData<ArrayList<CostHistory>> costHistory = new MutableLiveData<>();

    public static void getCostHistory(String date) {
        ArrayList<CostHistory> result = new ArrayList<>();

        Constant.CostHistory.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    result.add(dataSnapshot.getValue(CostHistory.class));
                }
                costHistory.setValue(result);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public static MutableLiveData<Integer[]> model = new MutableLiveData<>();


    public static void getTodayTotalSell() {
        Integer[] amount = {0, 0};

        Task task2 = Constant.thisMonthSell.get().addOnCompleteListener(task -> {

            Integer value1 = task.getResult().getValue(Integer.class);
            Optional<Integer> optional = Optional.ofNullable(value1);
            amount[0] = optional.orElse(0);

        });


        Task task3 = Constant.thisMonthCost.get().addOnCompleteListener(task -> {
            Integer value2 = task.getResult().getValue(Integer.class);
            Optional<Integer> optional = Optional.ofNullable(value2);
            amount[1] = optional.orElse(0);
        });


        Tasks.whenAllComplete(task2, task3).addOnCompleteListener(task -> {
            model.setValue(amount);
        });


    }


    public static void getDataToUpdate(DatabaseReference baseRef, int updateAmount) {
        baseRef.get().addOnCompleteListener(task -> {
            Integer value = task.getResult().getValue(Integer.class);
            if (value == null) {
                value = 0;
            }
            value += updateAmount;
            baseRef.setValue(value);
        });
    }


    //Only for Profile Fragments
    public static MutableLiveData<ArrayList<Integer>> thisMonthCost = new MutableLiveData<>();
    public static ArrayList<String> profileCostlDates = new ArrayList<>();
    public static int thisMonthTotalCost = 0;
    public static void getMonthCost(int modify) {
        ArrayList<Integer> list = new ArrayList<>();
        Constant.dbRef.child("MonthHistory").child("MonthCost").child(GetDate.getMonth(modify)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add(dataSnapshot.getValue(Integer.class));
                    thisMonthTotalCost  += dataSnapshot.getValue(Integer.class);
                    profileCostlDates.add(dataSnapshot.getKey());
                }
                thisMonthCost.setValue(list);
            }
        });

    }


    public static MutableLiveData<ArrayList<Integer>> thisMonthSell = new MutableLiveData<>();
    public static ArrayList<String> profileSellDates = new ArrayList<>();
    public static int thisMonthTotalSell = 0;

    public static void getMonthSell(int modify) {
        ArrayList<Integer> list = new ArrayList<>();
        Constant.dbRef.child("MonthHistory").child("MonthSell").child(GetDate.getMonth(modify)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add(dataSnapshot.getValue(Integer.class));
                    thisMonthTotalSell  += dataSnapshot.getValue(Integer.class);
                    profileSellDates.add(dataSnapshot.getKey());
                }

                thisMonthSell.setValue(list);
            }
        });

    }
}
