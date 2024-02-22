package com.hanifsapp.hisabee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AADataElement;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;
import com.hanifsapp.hisabee.databinding.FragmentHomeBinding;
import com.hanifsapp.hisabee.utility.GetDate;


public class HomeFragment extends Fragment {
    static FragmentHomeBinding binding;
    private AAChartModel aaChartModelGraph;
    private AAChartModel aaChartModelPie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);


        GetDate.getDate(0);
        binding.dateTextview.setText(GetDate.date);
        GetHistory.getTodayTotalSell();
        GetHistory.getSoldHistory();

        showChart();
        return binding.getRoot();
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GetHistory.model.observe(this, modelArray -> binding.aaChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(new AASeriesElement[]{
                new AASeriesElement().data(modelArray)
        }));

        GetHistory.getGraph.observe(this, seriesData -> {
            Integer[] dataArray = seriesData.toArray(new Integer[seriesData.size()]);
            aaChartModelGraph.setCategories(GetHistory.dates.toArray(new String[0]));
            binding.graphChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(new AASeriesElement[]{
                    new AASeriesElement().data(dataArray)
            });
        });

        super.onCreate(savedInstanceState);
    }



    private void showChart(){
        aaChartModelPie = new AAChartModel()
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
                                new AADataElement().name("Sell").y(0),
                                new AADataElement().name("Cost").y(0),
                        }),
                });
        binding.aaChartView.aa_drawChartWithChartModel(aaChartModelPie);


        aaChartModelGraph = new AAChartModel()
                .chartType(AAChartType.Area)
                .backgroundColor("")
                .categories(new String[]{"0", "1", "2", "3"})
                .axesTextColor("#FFBB86FC")
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Sells")
                                .data(new Object[]{0,0,0,0}),

                });

        binding.graphChartView.aa_drawChartWithChartModel(aaChartModelGraph);
    }
}