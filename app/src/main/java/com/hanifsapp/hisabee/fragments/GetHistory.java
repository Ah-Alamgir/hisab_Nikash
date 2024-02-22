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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.model.CostHistory;

import java.util.ArrayList;
import java.util.Optional;

public class GetHistory {

    public static MutableLiveData<AAChartModel> getGraph = new MutableLiveData<>();

    public static void getSoldHistory() {
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        Task task4 = Constant.todaySellHistory.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dates.add(dataSnapshot.getKey());
                    amount.add(dataSnapshot.getValue(Integer.class));
                }
            }
        });


        Tasks.whenAllComplete(task4).addOnCompleteListener(task -> {
            AAChartModel aaChartModel = new AAChartModel()
                    .chartType(AAChartType.Area)
                    .title("Today Sell History")
                    .backgroundColor("")
                    .categories(dates.toArray(new String[0]))
                    .dataLabelsEnabled(false)
                    .yAxisGridLineWidth(0f)
                    .series(new AASeriesElement[]{
                            new AASeriesElement()
                                    .name("Sells")
                                    .data(amount.toArray(new Integer[0])),
                    });


            getGraph.setValue(aaChartModel);
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


    public static MutableLiveData<AAChartModel> model = new MutableLiveData<>();


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
//                                    new AADataElement().name("Due").y(amount[2]),

                                    // Add more data elements as needed
                            }),
                    });

            model.setValue(aaChartModel);
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


}
