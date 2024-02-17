package com.hanifsapp.hisabee.fragments;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AADataElement;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.model.CostHistory;
import com.hanifsapp.hisabee.model.SoldHistory;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;
import java.util.Objects;

public class GetHistory {


    public static ArrayList<SoldHistory> getSoldHistory() {
        ArrayList<SoldHistory> items = new ArrayList<SoldHistory>();
        Constant.todaySellHistory.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SoldHistory soldHistory = new SoldHistory();
                    soldHistory.setDate(dataSnapshot.getKey());
                    soldHistory.setPrice(dataSnapshot.getValue(Integer.class));
                    items.add(soldHistory);

                }
            }
        });
        return items;
    }


    public static ArrayList<CostHistory> getCostHistory() {
        ArrayList<CostHistory> result = new ArrayList<CostHistory>();
        try {
            Constant.todayCostHistory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (Objects.equals(dataSnapshot.getKey(), GetDate.getDate())) {
                            result.add(dataSnapshot.getValue(CostHistory.class));
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }


        return result;
    }




    public static MutableLiveData<AAChartModel> model = new MutableLiveData<>();
    public static void getTodayTotalSell() {
        int[] amount = {0,0,0};
        Task task1=Constant.todaySell.child(GetDate.date).get().addOnCompleteListener(task -> {
            try {
                amount[0] = task.getResult().getValue(Integer.class);
            }catch (Exception e) {
                amount[0] = 0;
            }
        });


       Task task2= Constant.todayDue.child(GetDate.date).get().addOnCompleteListener(task -> {
            try {
                amount[1] = task.getResult().getValue(Integer.class);
            }catch (Exception e) {
                amount[1] = 0;
            }
        });


        Task task3 = Constant.todayCost.child(GetDate.date).get().addOnCompleteListener(task -> {
            try {
                amount[2] = task.getResult().getValue(Integer.class);
            }catch (Exception e) {
                amount[2] = 0;
            }
        });

        Tasks.whenAllComplete(task1,task2,task3).addOnCompleteListener(task -> {
            AAChartModel aaChartModel = new AAChartModel()
                    .chartType(AAChartType.Pie)
                    .polar(true)
                    .backgroundColor("")
                    .axesTextColor("#FFBB86FC")
                    .dataLabelsEnabled(true)
                    .yAxisGridLineWidth(0f)
                    .series(new AASeriesElement[]{
                            new AASeriesElement()
                                    .name("Data")
                                    .data(new AADataElement[]{
                                    new AADataElement().name("Sell").y(amount[0]),
                                    new AADataElement().name("Cost").y(amount[1]),
                                    new AADataElement().name("Due").y(amount[2]),

                                    // Add more data elements as needed
                            }),
                    });
            model.setValue(aaChartModel);
        });


    }


}
