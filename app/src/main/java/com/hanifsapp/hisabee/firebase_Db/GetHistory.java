package com.hanifsapp.hisabee.firebase_Db;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.model.SoldHistory;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;
import java.util.Objects;

public class GetHistory {

    public static ArrayList<SoldHistory> getSoldHistory() {
        ArrayList<SoldHistory> items = new ArrayList<SoldHistory>();
        Constant.todayCostHistory.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SoldHistory history = dataSnapshot.getValue(SoldHistory.class);
                        items.add(history);
                }
            }

        });
        return items;
    }


    public static void getCostHistory() {
        try {
            Constant.todayCostHistory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> items = new ArrayList<String>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (Objects.equals(dataSnapshot.getKey(), GetDate.getDate())) {
                            items.add(dataSnapshot.getValue(String.class));
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }

    }
}
