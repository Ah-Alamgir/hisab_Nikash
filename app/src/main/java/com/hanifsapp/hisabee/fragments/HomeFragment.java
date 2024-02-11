package com.hanifsapp.hisabee.fragments;

import static com.hanifsapp.hisabee.utility.CustomChart.showChart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.charts.CircularGauge;
import com.hanifsapp.hisabee.activity.Sell;
import com.hanifsapp.hisabee.activity.StockActivity;
import com.hanifsapp.hisabee.activity.costCalculation;
import com.hanifsapp.hisabee.databinding.FragmentHomeBinding;
import com.hanifsapp.hisabee.denaPawna;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        displayChart();


        //handle ui releted work
        binding.selling.setOnClickListener(view -> startActivity(new Intent(getContext(), Sell.class)));
        binding.stockManage.setOnClickListener(view -> startActivity(new Intent(getContext(), StockActivity.class)));
        binding.costBooks.setOnClickListener(view -> startActivity(new Intent(getContext(), costCalculation.class)));
        binding.dueBooks.setOnClickListener(view -> startActivity(new Intent(getContext(), denaPawna.class)));

        return binding.getRoot();


    }


    private void displayChart() {
        binding.anyChartView.setProgressBar(binding.progressBar);
        String[] values = {"500", "250", "1800", "5685"};
        String[] labels = {"Due", "Cost", "Sell", "le"};
        CircularGauge gauge = showChart(values, labels);
        binding.anyChartView.setChart(gauge);
    }
}