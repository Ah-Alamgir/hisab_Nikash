package com.hanifsapp.hisabee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;
import com.hanifsapp.hisabee.databinding.FragmentHomeBinding;
import com.hanifsapp.hisabee.model.SoldHistory;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    static FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);


        GetDate.getDate(0);
        binding.dateTextview.setText(GetDate.date);
        GetHistory.getTodayTotalSell();
        GetHistory.getSoldHistory();


        return binding.getRoot();
    }


    public static void disPlayChart2() {
        ArrayList<SoldHistory> soldHistories = GetHistory.getSoldHistory();
        List<String> sellAmount = new ArrayList<String>();

        soldHistories.forEach(items -> {
            sellAmount.add(String.valueOf(items.getPrice()));
        });


        if (sellAmount.size() > 0) {
            String[] amountArray = sellAmount.toArray(new String[0]);
            AAChartModel aaChartModel = new AAChartModel()
                    .chartType(AAChartType.Area)
                    .title("Today Sell History")
                    .subtitle("Virtual Data")
                    .backgroundColor("")
                    .categories(new String[]{"Java", "Swift", "Python", "Ruby", "PHP", "Go", "C", "C#", "C++"})
                    .dataLabelsEnabled(false)
                    .yAxisGridLineWidth(0f)
                    .series(new AASeriesElement[]{
                            new AASeriesElement()
                                    .name("Tokyo")
                                    .data(amountArray),
                    });

            binding.graphChartView.aa_drawChartWithChartModel(aaChartModel);

        } else {

        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GetHistory.model.observe(this, aaChartModel -> {
            binding.aaChartView.aa_drawChartWithChartModel(aaChartModel);
        });

        super.onCreate(savedInstanceState);
    }
}