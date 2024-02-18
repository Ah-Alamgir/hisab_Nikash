package com.hanifsapp.hisabee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hanifsapp.hisabee.databinding.FragmentHomeBinding;
import com.hanifsapp.hisabee.utility.GetDate;


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




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GetHistory.model.observe(this, aaChartModel -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.aaChartView.aa_drawChartWithChartModel(aaChartModel);
        });

        GetHistory.getGraph.observe(this, aaChartModel -> {
            binding.graphChartView.aa_drawChartWithChartModel(aaChartModel);
        });

        super.onCreate(savedInstanceState);
    }
}