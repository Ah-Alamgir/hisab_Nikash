package com.hanifsapp.hisabee.firebase_Db;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.Autoload;

import java.util.ArrayList;
import java.util.Objects;

public class GetHistory {

    public static MutableLiveData<ArrayList<String>> todaySoldList = new MutableLiveData<ArrayList<String>>();
    public static MutableLiveData<ArrayList<String>> todayCostList = new MutableLiveData<ArrayList<String>>();

    public static void getSoldHistory() {
        try {
            Constant.todaySellHistory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> items = new ArrayList<String>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (Objects.equals(dataSnapshot.getKey(), Autoload.dates)){
                            items.add(dataSnapshot.getValue(String.class));
                        }

                    }
                    todaySoldList.setValue(new ArrayList<>());
                    todaySoldList.setValue(items);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }

    }


    public static void getCostHistory() {
        try {
            Constant.todayCostHistory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> items = new ArrayList<String>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (Objects.equals(dataSnapshot.getKey(), Autoload.dates)){
                            items.add(dataSnapshot.getValue(String.class));
                        }

                    }
                    todayCostList.setValue(new ArrayList<>());
                    todayCostList.setValue(items);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }

    }
}
