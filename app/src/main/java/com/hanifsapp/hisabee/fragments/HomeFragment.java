package com.hanifsapp.hisabee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AADataElement;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;
import com.hanifsapp.hisabee.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        displayChart();


        //handle ui releted work
//        binding.selling.setOnClickListener(view -> startActivity(new Intent(getContext(), Sell.class)));
//        binding.stockManage.setOnClickListener(view -> startActivity(new Intent(getContext(), StockActivity.class)));
//        binding.costBooks.setOnClickListener(view -> startActivity(new Intent(getContext(), CostHistoryActivity.class)));
//        binding.dueBooks.setOnClickListener(view -> startActivity(new Intent(getContext(), denaPawna.class)));

        return binding.getRoot();


    }


    private void displayChart() {
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
                                new AADataElement().name("Sell").y(5000),
                                new AADataElement().name("Buy").y(300),
                                new AADataElement().name("Due").y(2000),

                                // Add more data elements as needed
                        }),
                });

        binding.aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }
}